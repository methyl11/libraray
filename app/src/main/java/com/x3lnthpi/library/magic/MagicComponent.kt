package com.x3lnthpi.library.magic

import dagger.Component
import jakarta.inject.Named
import jakarta.inject.Singleton

@Singleton
@Component(modules = [MagicModule::class])
interface MagicComponent {

    @Named("Conjure")
    fun conjure(): Magic
    @Named("Fire")
    fun fire(): Magic

    // This allows injecting dependencies into classes like Activities or Fragments
    fun inject(application: MagicApplication)

}