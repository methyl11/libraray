package com.x3lnthpi.library

import com.x3lnthpi.library.magic.MagicComponent
import dagger.Component

@Component(dependencies = [SideKickComponent::class] ,modules = [WeaponModule::class, EnemyModule::class])
interface SceneComponent {
    fun getSideKick() : SideKicks
}