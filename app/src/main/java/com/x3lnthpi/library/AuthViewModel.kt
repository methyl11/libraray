package com.x3lnthpi.library

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

//object CurrentUser {
//    var user: FirebaseUser? = null
//}

class AuthViewModel : ViewModel() {
    private val auth : FirebaseAuth = Firebase.auth

    private val currentUser = auth.currentUser

//    init {
//        CurrentUser.user = auth.currentUser
//    }



//    // Use a MutableState for the user so that Composable functions can react to changes
//    private val _currentUser: MutableState<FirebaseUser?> = mutableStateOf(auth.currentUser)
//    val currentUser: State<FirebaseUser?> = _currentUser
//
//    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
//        _currentUser.value = firebaseAuth.currentUser
//    }
//
//    init {
//        auth.addAuthStateListener(authStateListener)
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        auth.removeAuthStateListener(authStateListener)
//    }
//
//    fun signOut() {
//        auth.signOut()
//    }
}