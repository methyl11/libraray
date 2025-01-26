package com.x3lnthpi.library

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.firebase.FirebaseApp

class MainActivityViewModel : ViewModel() {
    var uri : Uri? by mutableStateOf(null)
        private set

    fun updateUri(uri: Uri?){
        this.uri = uri
    }


//    init {
//        // Initialize Firebase and fetch Remote Config values
//        FirebaseApp.initializeApp(application)
//        fetchRemoteConfig()
//    }


}