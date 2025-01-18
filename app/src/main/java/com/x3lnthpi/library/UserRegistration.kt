package com.x3lnthpi.library

import dagger.Component

@Component
interface UserRegistration {
    fun getUserRegistrationService(): UserRegistrationService
    fun getEmailService(): EmailService

}