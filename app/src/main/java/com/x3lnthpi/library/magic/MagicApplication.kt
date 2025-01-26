package com.x3lnthpi.library.magic

import android.app.Application
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class MagicApplication : Application() {

    lateinit var magicComponent: MagicComponent

    override fun onCreate() {
        super.onCreate()

        //Firebase code for remote-config @Application level
        FirebaseApp.initializeApp(this)

        // Initialize Remote Config
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600 // Fetch every hour
        }
        remoteConfig.setConfigSettingsAsync(configSettings)


        // Fetch Remote Config values
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Remote Config fetched successfully")
                } else {
                    println("Failed to fetch Remote Config")
                }
            }

        // Set default values (fallback in case fetching fails)
        val configDefaults = mapOf(
            "Runpod_API_Keys" to "default_api_key", // Replace with a default key
            "Runpod_Endpoint" to "default_endpoint" // Replace with a default endpoint
        )
        remoteConfig.setDefaultsAsync(configDefaults)

        Log.d("MagicApplication", "MagicApplication initialized")
       // magicComponent = DaggerMagicComponent.builder().magicModule(MagicModule()).build()
        magicComponent = DaggerMagicComponent.builder().build()
    }
}