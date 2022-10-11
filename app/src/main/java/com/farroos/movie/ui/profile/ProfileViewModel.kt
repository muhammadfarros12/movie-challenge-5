package com.farroos.movie.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farroos.movie.data.SharedPreference
import com.farroos.movie.data.local.User

class ProfileViewModel(private val sharedPreference: SharedPreference): ViewModel() {

    private var _email = MutableLiveData<String>()
    val email : LiveData<String> get() = _email
    private var _username = MutableLiveData<String>()
    val usename : LiveData<String> get() = _username
    private var _fullname = MutableLiveData<String>()
    val fullname : LiveData<String> get() = _fullname
    private var _address = MutableLiveData<String>()
    val address : LiveData<String> get() = _address
    private var _id = MutableLiveData<Int>()
    val id : LiveData<Int> get() = _id
    private var _password = MutableLiveData<String>()
    val password : LiveData<String> get() = _password

    fun getUserData(){
        _email.value = sharedPreference.getPrefKey("email")
        _password.value = sharedPreference.getPrefKey("password")
        _username.value = sharedPreference.getPrefKey("username")
        _fullname.value = sharedPreference.getPrefKey("fullname")
        _address.value = sharedPreference.getPrefKey("address")
        _id.value = sharedPreference.getPrefKeyId("id")
    }

    fun sendDataToUpdate(): User{
        return User(
            id = id.value!!,
            username = usename.value!!,
            password = password.value!!,
            address = address.value!!,
            email = email.value!!,
            fullname = fullname.value!!,
        )
    }

}