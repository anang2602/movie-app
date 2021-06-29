package com.technicalassigments.moviewapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindorks.framework.mvvm.data.model.GenreResponse
import com.technicalassigments.moviewapp.data.repository.MainRepository
import com.technicalassigments.moviewapp.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val categories = MutableLiveData<Resource<GenreResponse>>()
    private val compositeDisposable = CompositeDisposable()

    private fun fetchCategory() {
        categories.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getMoviesGenre()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { res ->
                        categories.postValue(Resource.success(res))
                    }, {
                        categories.postValue(Resource.error("Something Went Wrong", null))
                    }
                )
        )
    }

    fun getCategories(): LiveData<Resource<GenreResponse>> {
        return categories
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}