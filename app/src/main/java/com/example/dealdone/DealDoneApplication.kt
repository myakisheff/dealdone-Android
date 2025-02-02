package com.example.dealdone

import android.app.Application
import com.example.dealdone.data.AppContainer
import com.example.dealdone.data.DefaultAppContainer

class DealDoneApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}