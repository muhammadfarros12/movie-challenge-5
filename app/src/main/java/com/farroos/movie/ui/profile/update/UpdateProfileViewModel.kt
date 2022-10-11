package com.farroos.movie.ui.profile.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farroos.movie.data.local.User
import com.farroos.movie.data.local.UserRepository
import com.farroos.movie.utils.Event
import kotlinx.coroutines.launch

class UpdateProfileViewModel(private val repository: UserRepository): ViewModel() {

    private var _saved = MutableLiveData<Event<Boolean>>()
    val saved : LiveData<Event<Boolean>> get() = _saved

    fun update(
        id: Int,
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
            password = password,
            id = id
        )

        viewModelScope.launch {
            repository.update(user)
        }

        _saved.value = Event(true)

    }

}