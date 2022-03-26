package com.technicalassigments.movieapp.domain.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


@Suppress("DEPRECATION")
class NetworkUtils(
    private val context: Context
) {
    fun checkForInternet(): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        fun checkConnectionApi21(): Boolean {
            var isConncted = false
            val info: Array<android.net.NetworkInfo> = connectivityManager.allNetworkInfo
            for (i in info) {
                if (i.state == android.net.NetworkInfo.State.CONNECTED) {
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
}