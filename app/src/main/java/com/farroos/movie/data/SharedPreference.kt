package com.farroos.movie.data

import android.annotation.SuppressLint
import android.content.Context
import com.farroos.movie.data.local.User

class SharedPreference(context: Context) {

    private val pref = "authentication"
    private val sharedPreference = context.getSharedPreferences(pref, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun saveKey(user: User) {
        val editor = sharedPreference.edit()
        editor.putString("username", user.username)
        editor.putString("email", user.email)
        editor.putString("password", user.password)
        editor.putString("fullname", user.fullname)
        editor.putString("address", user.address)
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun saveKeyState(status: Boolean) {
        val editor = sharedPreference.edit()
        editor.putBoolean("login_status", status)
        editor.apply()
    }


    @SuppressLint("CommitPrefEdits")
    fun getPrefKey(key: String): String? {
        return sharedPreference.getString(key, "data kosong")
    }

    @SuppressLint("CommitPrefEdits")
    fun getPrefKeyId(key: String): Int? {
        return sharedPreference.getInt(key, 0)
    }

    @SuppressLint("CommitPrefEdits")
    fun getPrefKeyStatus(key: String): Boolean {
        return sharedPreference.getBoolean(key, false)
    }

    @SuppressLint("CommitPrefEdits")
    fun clearUsername() {
        val editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
    }

}