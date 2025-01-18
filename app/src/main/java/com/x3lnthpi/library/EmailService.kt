package com.x3lnthpi.library

import jakarta.inject.Inject

class EmailService @Inject constructor() {
    fun send(to: String, from: String, body: String?){
        print("Email Sent")
    }
}