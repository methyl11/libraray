package com.x3lnthpi.library.guns

import dagger.BindsInstance
import dagger.Component
import jakarta.inject.Named

@Component(modules = [GunModule::class, BulletsModule::class])
interface GunComponent {
    @Named("ak47") fun getAk47(): Gun
    @Named("M4") fun getM4(): Gun

    @Component.Builder
    interface Builder{
        fun module(module: GunModule): Builder
        fun bulletsModule(module: BulletsModule): Builder //Add the Bullets Module to the builder
//        @BindsInstance // Use @BindsInstance
//        fun bullets(@BulletsQualifier bullets: Int): Builder
        fun build(): GunComponent
    }

}