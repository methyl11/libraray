package com.x3lnthpi.library

import jakarta.inject.Inject

interface Weapon {
    fun doDamage(damageValue: Float): Float
}

class Axe @Inject constructor(): Weapon{
    override fun doDamage(damageValue: Float): Float {
        print("Axe does $damageValue damage")
        return damageValue
    }
}

class Arrow @Inject constructor(): Weapon{
    override fun doDamage(damageValue: Float): Float {
        print("Arrows does $damageValue damage")
        return damageValue
    }
}

class Sword @Inject constructor(): Weapon{
    override fun doDamage(damageValue: Float): Float {
        print("Sword weapon")
        return damageValue
    }
}