package com.example.sinchdemofirst.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.sinch.android.rtc.*
import com.sinch.android.rtc.internal.natives.jni.CallClient


class SinchService : Service(), SinchClientListener {

    companion object {
        private const val SINCH_APP_KEY = "9ec7428e-df01-48aa-b8ed-34f4df6226af"
        private const val SINCH_APP_SECRET = "r1BJgQn+sEmVjCX+fPs2lQ=="
        private const val SINCH_ENVIRONMENT = "clientapi.sinch.com"
    }

    private var sinchClient: SinchClient? = null
    private var userId: String = ""

    private fun startInternal(username: String) {
        if (sinchClient == null) {
            createClient(username)
        }
        sinchClient?.start()
    }

    private fun createClient(username: String) {
//        val pushConfiguration = cratePushConfiguration()
        userId = username
        val sinchClient: SinchClient = Sinch.getSinchClientBuilder().context(this)
            .applicationKey(SINCH_APP_KEY)
            .applicationSecret(SINCH_APP_SECRET)
            .environmentHost(SINCH_ENVIRONMENT)
            .userId("<user id>")
            .build()
    }


    override fun onLogMessage(p0: Int, p1: String?, p2: String?) {
        TODO("Not yet implemented")
    }


    private var sinchClientInitializationListener:
            SinchClientInitializationListener? = null

    interface SinchClientInitializationListener {
        fun onStartedSuccessfully()
        fun onFailed(error: SinchError)
    }

    inner class SinchServiceBinder : Binder() {
        fun start(username: String) {
            startInternal(username)
        }

        fun setClientInitializationListener(
            initializationListener:
            SinchClientInitializationListener?
        ) {
            sinchClientInitializationListener = initializationListener
        }

    }


    override fun onClientStarted(p0: SinchClient?) {
        sinchClientInitializationListener?.onStartedSuccessfully()
    }

    override fun onClientStopped(p0: SinchClient?) {
        TODO("Not yet implemented")
    }

    override fun onClientFailed(p0: SinchClient?, p1: SinchError?) {
        sinchClientInitializationListener?.onFailed(p1!!)
    }

    override fun onRegistrationCredentialsRequired(p0: SinchClient?, p1: ClientRegistration?) {
        TODO("Not yet implemented")
    }

    private val sinchServiceBinder = SinchServiceBinder()

    override fun onBind(intent: Intent?): IBinder {
        return sinchServiceBinder
    }





}
