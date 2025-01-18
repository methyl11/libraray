package com.x3lnthpi.library

import dagger.Module
import dagger.Provides
import jakarta.inject.Named

@Module
class WeaponModule {

    @Named("Axe")
    @Provides
    fun getAxe() : Weapon{
        return Axe()
    }

    @Named("Sword")
    @Provides
    fun getSword(): Weapon{
        return Sword()
    }

    @Named("Arrow")
    @Provides
    fun getArrow(): Weapon{
        return Arrow()
    }
}