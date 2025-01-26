package com.x3lnthpi.library

import com.x3lnthpi.library.magic.MagicApplication
import jakarta.inject.Inject

interface SideKicks {
    fun attackEnemy()
}

class Mage @Inject constructor(val magicApplication: MagicApplication): SideKicks{
    override fun attackEnemy() {
        magicApplication.magicComponent.conjure()
        print("Mage can do Magic")
    }
}