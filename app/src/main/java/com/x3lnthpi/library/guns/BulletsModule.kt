package com.x3lnthpi.library.guns

import dagger.Module
import dagger.Provides

@Module
class BulletsModule(private val bullets: Int) {
    @Provides
    @BulletsQualifier
    fun provideBullets(): Int = bullets //Provides the bullets
}