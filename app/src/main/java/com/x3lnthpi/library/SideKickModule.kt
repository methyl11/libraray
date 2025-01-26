package com.x3lnthpi.library

import com.x3lnthpi.library.magic.MagicApplication
import dagger.Module
import dagger.Provides
import jakarta.inject.Inject

@Module
class SideKickModule {
    
    @Provides
    fun addSideKick() : SideKicks{
        return Mage(
            @Inject MagicApplication()
        )
    }
}