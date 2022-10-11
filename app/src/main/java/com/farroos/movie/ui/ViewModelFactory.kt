package com.farroos.movie.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farroos.movie.data.local.UserRepository

class ViewModelFactory(private val context: Context): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return modelClass.getConstructor(UserRepository::class.java)
                .newInstance(UserRepository.getInstance(context))
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}