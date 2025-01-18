package com.x3lnthpi.library
import jakarta.inject.Inject

class UserRegistrationService @Inject constructor(
    private val userRepository: UserRepository,
    private val emailService: EmailService
) {

    fun registerUser(email: String, password: String){
        userRepository.saveUser(email, password)
        emailService.send(email,"Rahul", "X3lnThPi")
    }
}