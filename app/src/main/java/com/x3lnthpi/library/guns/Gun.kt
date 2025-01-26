package com.x3lnthpi.library.guns

import dagger.assisted.AssistedInject
import jakarta.inject.Inject

interface Gun {
    var bullets : Int
    fun shoot()
}

class Ak47 @Inject constructor(override var bullets: Int): Gun{
    override fun shoot() {
        println("Shooting with Ak47")
    }
}

class M4 @Inject constructor(override var bullets: Int): Gun{
    override fun shoot() {
        println("Shooting with M4")
    }
}