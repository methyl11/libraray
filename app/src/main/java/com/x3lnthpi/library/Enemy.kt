package com.x3lnthpi.library

import jakarta.inject.Inject


interface Enemy {
    fun attack(attackValue: Float): Float
}

class Orc @Inject constructor(): Enemy{
    override fun attack(attackValue: Float): Float {
        print("Orc melee attack")
        return attackValue
    }

}

class Archer @Inject constructor(): Enemy{
    override fun attack(attackValue: Float): Float {
        print("Archer shooting Arrows")
        return attackValue
    }
}

class Demons @Inject constructor(): Enemy{
    override fun attack(attackValue: Float): Float {
        print("Demon doing attack")
        return attackValue
    }
}