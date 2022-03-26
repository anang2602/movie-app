package com.technicalassigments.movieapp.domain.utils

import android.content.Context
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResultType, RequestType>
constructor(private val networkUtils: NetworkUtils) {

    fun asFlow() = flow {
        emit(Resource.loading(null))
        val dbValue = loadFromDb().first()
        try {
            if (shouldFetch(dbValue)) {
                emit(Resource.loading(dbValue))
                if (networkUtils.checkForInternet()) {
                    when (val apiResponse = fetchFromNetwork()) {
                        is ApiSuccessResponse -> {
                            withContext(Dispatchers.IO) {
                                saveNetworkResult(processResponse(apiResponse))
                            }
                            emitAll(loadFromDb().map { Resource.success(it) })
                        }
                        is ApiErrorResponse -> {
                            onFetchFailed()
                            emitAll(loadFromDb().map {
                                Resource.error(
                                    apiResponse.errorMessage,
                                    it
                                )
                            })
                        }
                        is ApiEmptyResponse -> {
                            emitAll(loadFromDb().map { Resource.success(it) })
                        }
                    }
                } else {
                    emitAll(loadFromDb().map {
                        Resource.offline(
                            "Anda tidak terhubung ke internet",
                            it
                        )
                    })
                }
            } else {
                emitAll(loadFromDb().map { Resource.success(it) })
            }
        } catch (e: Exception) {
            onFetchFailed()
            emitAll(loadFromDb().map {
                Resource.error(
                    e.localizedMessage!!,
                    it
                )
            })
        }
    }.flowOn(Dispatchers.IO)

    protected open fun onFetchFailed() {}

    @WorkerThread
    abstract suspend fun saveNetworkResult(item: RequestType)

    @WorkerThread
    protected fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @WorkerThread
    protected abstract suspend fun fetchFromNetwork(): ApiResponse<RequestType>

    @MainThread
    protected abstract fun loadFromDb(): Flow<ResultType>
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> offline(msg: String, data: T?): Resource<T> {
            return Resource(Status.OFFLINE, data, msg)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    OFFLINE
}