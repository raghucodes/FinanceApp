package com.example.yahoofinance.ui.networkstate

import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import androidx.annotation.RequiresApi

object NetworkStateHolder : NetworkState {

    private lateinit var holder: NetworkStateImp

    override val isConnected: Boolean
        get() = holder.isConnected
    override val network: Network?
        get() = holder.network
    override val networkCapabilities: NetworkCapabilities?
        get() = holder.networkCapabilities
    override val linkProperties: LinkProperties?
        get() = holder.linkProperties

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Application.registerConnectivityMonitor() {
        holder = NetworkStateImp()
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), NetworkCallbackImp(holder))
    }

}