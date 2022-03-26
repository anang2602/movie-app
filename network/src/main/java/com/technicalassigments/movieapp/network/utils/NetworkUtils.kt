package com.technicalassigments.movieapp.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import okhttp3.ResponseBody
import org.json.JSONObject

fun getErrorMessage(responseBody: ResponseBody, key: String): String? {
    return try {
        val jsonObject = JSONObject(responseBody.string())
        jsonObject.getString(key)
    } catch (e: java.lang.Exception) {
        e.message
    }
}

fun checkForInternet(context: Context): Boolean {

    // register activity with the connectivity manager service
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun checkConnectionApi21(): Boolean {
        var isConncted = false
        val info: Array<NetworkInfo> = connectivityManager.allNetworkInfo
        for (i in info) {
            if (i.state == NetworkInfo.State.CONNECTED) {
                isConncted = true
            }
        }
        return isConncted
    }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        checkConnectionApi21()
    }

}