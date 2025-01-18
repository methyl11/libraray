package com.x3lnthpi.library.magic

import android.app.Application

class MagicApplication : Application() {

    lateinit var magicApplication : MagicComponent

    override fun onCreate() {
        super.onCreate()
        magicApplication = DaggerMagicComponent.builder().magicModule(MagicModule()).build()
    }
}