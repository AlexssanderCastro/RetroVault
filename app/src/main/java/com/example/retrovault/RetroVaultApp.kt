package com.example.retrovault

import android.app.Application
import com.example.retrovault.utils.AppContainer
import com.example.retrovault.utils.AppContainerImpl

class RetroVaultApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}

