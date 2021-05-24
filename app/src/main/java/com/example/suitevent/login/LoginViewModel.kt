package com.example.suitevent.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.suitevent.R
import com.example.suitevent.util.Event
import timber.log.Timber

class LoginViewModel : ViewModel() {

    private val _snackBarMessage = MutableLiveData<Event<Int>>()
    val snackBarMessage: LiveData<Event<Int>> = _snackBarMessage

    private val _isPalindrome = MutableLiveData<Event<Int>>()
    val isPalindrome: LiveData<Event<Int>> = _isPalindrome

    fun login(username: String) {
        if (username.isNotBlank()) {
            _snackBarMessage.value = Event(0)
        } else {
            _snackBarMessage.value = Event(R.string.error_login)
        }
    }

    fun checkPalinDrome(inputText: String) {
        _isPalindrome.value = when {
            inputText.isBlank() -> {
                Event(R.string.palindrome_blank)
            }

            isPalindrome(inputText) -> Event(R.string.is_palindrome)

            else -> Event(R.string.not_palindrome)
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val ommitSpace = text.replace(" ", "")
        Timber.d(ommitSpace)
        return if (ommitSpace.length % 2 == 0) {
            checPalindromeEventLength(ommitSpace)
        } else {
            checPalindromeOddLength(ommitSpace)
        }
    }

    private fun checPalindromeEventLength(text: String): Boolean {
        val halfFirstText = text.substring(0, (text.length / 2)).lowercase()
        val halfLastText = text.substring(text.length / 2).lowercase().reversed()
        Timber.d("$halfFirstText == $halfLastText")
        return halfFirstText == halfLastText
    }

    private fun checPalindromeOddLength(text: String): Boolean {
        val halfFirstText = text.substring(0, (text.length / 2)).lowercase()
        val halfLastText = text.substring((text.length / 2) + 1).lowercase().reversed()
        Timber.d("$halfFirstText == $halfLastText")
        return halfFirstText == halfLastText
    }
}