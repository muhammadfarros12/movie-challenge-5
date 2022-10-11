package com.farroos.movie.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farroos.movie.data.SharedPreference

@Suppress("UNCHECKED_CAST")
class ProfileViewModelProvider(private val sharedPreference: SharedPreference) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(sharedPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}