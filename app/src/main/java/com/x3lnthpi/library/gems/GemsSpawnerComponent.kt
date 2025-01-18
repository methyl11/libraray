package com.x3lnthpi.library.gems

import com.x3lnthpi.library.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [GemsModule::class])
interface GemsSpawnerComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance noOfGems: Int): GemsSpawnerComponent
    }
}