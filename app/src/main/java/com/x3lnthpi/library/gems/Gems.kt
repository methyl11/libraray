package com.x3lnthpi.library.gems

import jakarta.inject.Inject
import jakarta.inject.Named

interface Gems {
    fun createGem()
}

@Named("Ruby")
class Ruby @Inject constructor(): Gems{
    override fun createGem() {
        print("created Ruby")
    }
}

@Named("Sapphire")
class Sapphire(private val noOfGem: Int): Gems{
    override fun createGem() {
        print("Created $noOfGem of Sapphires")
    }
}