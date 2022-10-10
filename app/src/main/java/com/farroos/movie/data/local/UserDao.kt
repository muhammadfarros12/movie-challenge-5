package com.farroos.movie.data.local

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE :password")
    fun login(email: String, password: String): User

}