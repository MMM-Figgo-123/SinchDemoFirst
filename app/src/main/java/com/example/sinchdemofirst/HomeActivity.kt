package com.example.sinchdemofirst

import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.sinchdemofirst.databinding.ActivityHomeBinding
import com.example.sinchdemofirst.services.SinchService
import com.sinch.android.rtc.SinchError

class HomeActivity : AppCompatActivity(),ServiceConnection {

    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        bindService(
            Intent(this, SinchService::class.java),
            this,
            BIND_AUTO_CREATE
        )
        binding.loginButton.setOnClickListener {
            serviceBinder?.start(binding.enterUsernameEditText.text.toString())
        }

    }



    private var serviceBinder: SinchService.SinchServiceBinder? = null
    private var serviceInitializationListener: SinchService.SinchClientInitializationListener? = null

    override fun onServiceConnected(componentName: ComponentName?,
                                    binder: IBinder?) {
        serviceBinder = binder as SinchService.SinchServiceBinder?
        serviceBinder?.setClientInitializationListener(serviceInitializationListener)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        TODO("Not yet implemented")
    }



}