package com.x3lnthpi.library.magic

import dagger.Module
import dagger.Provides
import jakarta.inject.Named
import jakarta.inject.Singleton


@Module
class MagicModule {

    @Singleton
    @Named("Conjure")
    @Provides
    fun conjure(): Magic{
        return Conjure()
    }

    @Singleton
    @Named("Fire")
    @Provides
    fun fire(): Magic{
        return FireMagic()
    }

}