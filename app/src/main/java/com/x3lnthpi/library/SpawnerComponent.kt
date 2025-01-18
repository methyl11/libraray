package com.x3lnthpi.library

import dagger.Component
import jakarta.inject.Named

@Component(
    modules = [EnemyModule::class,
        WeaponModule::class]
)
interface SpawnerComponent {
    //fun inject(mainActivity: MainActivity)
    @Named("Demons") fun spawnEnemy(): Enemy
    @Named("Arrow") fun spawnWeapon(): Weapon
}