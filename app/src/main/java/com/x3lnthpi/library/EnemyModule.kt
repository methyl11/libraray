package com.x3lnthpi.library

import dagger.Module
import dagger.Provides
import jakarta.inject.Named

@Module
class EnemyModule {

    @Named("Archer")
    @Provides
    fun getArcher(): Enemy{
        return Archer()
    }

    @Named("Orc")
    @Provides
    fun getOrc(): Enemy{
        return Orc()
    }

    @Named("Demons")
    @Provides
    fun getDemon(): Enemy{
        return Demons()
    }
}