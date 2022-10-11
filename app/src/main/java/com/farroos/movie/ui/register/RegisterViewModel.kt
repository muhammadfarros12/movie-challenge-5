package com.farroos.movie.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farroos.movie.data.local.User
import com.farroos.movie.data.local.UserRepository
import com.farroos.movie.utils.Event
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    private var _saved = MutableLiveData<Event<Boolean>>()
    val saved: LiveData<Event<Boolean>> get() = _saved

    fun saved(
        userName: String,
        fullname: String,
        email: String,
        password: String,
        address: String
    ) {

        if (userName.isEmpty() || fullname.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()) {
            _saved.value = Event(false)
            return
        }

        val user = User(
            username = userName,
            fullname = fullname,
            email = email,
            address = address,
            password = password
        )

        viewModelScope.launch {
            repository.save(user)
        }

        _saved.value = Event(true)

    }


}