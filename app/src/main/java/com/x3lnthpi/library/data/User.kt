package com.x3lnthpi.library.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey val uid: String,
    @ColumnInfo(name= "first_name") val firstName: String?,
    @ColumnInfo(name= "last_name") val lastName: String?,
    @ColumnInfo(name = "age") val age: Int?
)