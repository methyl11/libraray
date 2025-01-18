package com.x3lnthpi.library


import jakarta.inject.Inject

class UserRepository @Inject constructor() {
    fun saveUser(email: String, password: String){
        print("User Saved in DB")
    }
}