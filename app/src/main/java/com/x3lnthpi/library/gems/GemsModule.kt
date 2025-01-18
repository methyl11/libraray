package com.x3lnthpi.library.gems

import dagger.Module
import dagger.Provides
import jakarta.inject.Named

@Module
class GemsModule() {

    @Named("Sapphire")
    @Provides
    fun createGem(noOfGems : Int): Gems{
        return Sapphire(noOfGems)
    }

    @Named("Ruby")
    @Provides
    fun creteGem(): Gems{
        return Ruby()
    }
}