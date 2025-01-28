package com.x3lnthpi.library.magic

import android.app.Application
import android.util.Log
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.initialize
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.google.firebase.storage.storage

class MagicApplication : Application() {

    lateinit var magicComponent: MagicComponent

    override fun onCreate() {
        super.onCreate()

        val auth : FirebaseAuth = Firebase.auth

        val currentUser = auth.currentUser


        //Firebase Appcheck
        Firebase.initialize(context = this)

        //Appcheck Debug
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )

//        Firebase.appCheck.installAppCheckProviderFactory(
//            PlayIntegrityAppCheckProviderFactory.getInstance(),
//        )

        Firebase.appCheck.setTokenAutoRefreshEnabled(true) // Recommended for testing and production


        var authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                // User is signed in!  Token is available when needed.
                // Update your app's state (e.g., in a ViewModel)
                // ... navigate to the main part of the app ...
            } else {
                // User is signed out
                // Redirect to login screen
            }
        }


        //Firebase code for remote-config @Application level
        FirebaseApp.initializeApp(this)

        // Initialize Firebase storage
        val db = Firebase.storage

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