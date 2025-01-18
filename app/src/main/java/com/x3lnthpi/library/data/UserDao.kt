package com.x3lnthpi.library.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    suspend fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user")
    suspend fun getEverything() : List<User>

    @Query("SELECT * FROM user WHERE uid = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getFirstUser(): User?
}