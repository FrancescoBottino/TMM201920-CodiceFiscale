package it.uniparthenope.studenti.francescobottino001.codicefiscale.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ConnectivityCheck {
    companion object {
        private val REACHABILITY_SERVER = "https://www.google.com"

        fun hasInternetConnected(context: Context): Boolean {
            if (hasNetworkAvailable(
                    context
                )
            ) {
                try {
                    val connection = URL(REACHABILITY_SERVER).openConnection() as HttpURLConnection
                    connection.setRequestProperty("User-Agent", "Test")
                    connection.setRequestProperty("Connection", "close")
                    connection.connectTimeout = 1000
                    connection.connect()
                    Log.d(TAG, "hasInternetConnected: ${(connection.responseCode == 200)}")
                    return (connection.responseCode == 200)
                } catch (e: IOException) {
                    Log.e(TAG, "Error checking internet connection", e)
                }
            } else {
                Log.w(TAG, "No network available!")
            }
            Log.d(TAG, "hasInternetConnected: false")
            return false
        }

        private fun hasNetworkAvailable(context: Context): Boolean {
            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }
                    }
                }
            }
            return result
        }
    }
}

