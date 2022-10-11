package com.farroos.movie.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE :password")
    fun login(email: String, password: String): User

    @Update
    suspend fun update(user: User)
}