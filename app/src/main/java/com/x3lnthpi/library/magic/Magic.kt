package com.x3lnthpi.library.magic

import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.inject.Singleton

interface Magic {
    fun castSpell()
}

@Named("Conjure")
@Singleton
class Conjure @Inject constructor() : Magic {
    override fun castSpell() {
        print("Conjuring magical element")
    }
}

@Named("Fire")
@Singleton
class FireMagic @Inject constructor(): Magic{
    override fun castSpell() {
        print("Cast Fire")
    }

}