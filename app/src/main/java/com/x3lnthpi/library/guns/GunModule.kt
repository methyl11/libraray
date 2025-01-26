package com.x3lnthpi.library.guns

import dagger.Module
import dagger.Provides
import jakarta.inject.Named

@Module
class GunModule {

    @Named("ak47")
    @Provides
    fun providesAk47(@BulletsQualifier bullets: Int): Gun = Ak47(bullets)

    @Named("M4")
    @Provides
    fun providesM4(@BulletsQualifier bullets: Int): Gun = M4(bullets)
}