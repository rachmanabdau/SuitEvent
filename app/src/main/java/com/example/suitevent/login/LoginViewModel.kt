package com.example.suitevent.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.suitevent.R
import com.example.suitevent.util.Event

class LoginViewModel : ViewModel() {

    private val _snackBarMessage = MutableLiveData<Event<Int>>()
    val snackBarMessage: LiveData<Event<Int>> = _snackBarMessage

    fun login(username: String) {
        if (username.isNotBlank()) {
            _snackBarMessage.value = Event(0)
        } else {
            _snackBarMessage.value = Event(R.string.error_login)
        }
    }
}