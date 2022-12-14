package com.farroos.movie.data.local

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    companion object{
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(context: Context): UserRepository? {
            return instance?: synchronized(UserRepository::class.java){
                if (instance == null){
                    val database = UserDatabase.getInstance(context)
                    instance = UserRepository(database.userDao())
                }
                return instance
            }
        }

    }

    suspend fun save(user: User){
        withContext(Dispatchers.IO){
            userDao.insert(user)
        }
    }

    suspend fun update(user: User){
        withContext(Dispatchers.IO){
            userDao.update(user)
        }
    }

    fun verifyLogin(email: String, password: String): User{
        return userDao.login(email, password)
    }


}