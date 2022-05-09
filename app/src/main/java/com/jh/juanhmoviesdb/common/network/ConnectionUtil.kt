package com.jh.juanhmoviesdb.common.network

import android.content.Context
import android.net.ConnectivityManager

class ConnectionUtil {
    fun isConnected(context: Context?): Boolean {
        if (context != null) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
        return false
    }
}