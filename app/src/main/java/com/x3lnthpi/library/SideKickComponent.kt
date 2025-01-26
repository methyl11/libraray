package com.x3lnthpi.library

import dagger.Component

@Component(modules = [SideKickModule::class])
interface SideKickComponent {
    fun getSideKick() : SideKicks
}