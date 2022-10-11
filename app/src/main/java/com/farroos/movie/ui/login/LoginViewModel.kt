package com.farroos.movie.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farroos.movie.data.local.User
import com.farroos.movie.data.local.UserRepository
import com.farroos.movie.data.resource.Resource
import java.util.concurrent.Executors

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private var _loginStatus = MutableLiveData<Resource<User>>()
    val loginStatus: LiveData<Resource<User>> get() = _loginStatus

    fun login(email: String, password: String) {

        val executor = Executors.newFixedThreadPool(1)
        executor.execute {
            _loginStatus.postValue(Resource.loading(null))
            try {
                val data = repository.verifyLogin(email, password)
                _loginStatus.postValue(Resource.success(data, 0))
            } catch (e: Exception) {
                _loginStatus.postValue(e.message?.let { Resource.error(null, it) })
            }
        }
    }

}