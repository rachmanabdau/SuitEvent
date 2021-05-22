package com.example.suitevent.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.suitevent.R
import com.example.suitevent.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var landingViewModel: LoginViewModel

    @Before
    fun setupViewModel() {
        landingViewModel = LoginViewModel()
    }

    @Test
    fun login_withBlankName_resultError() {
        // GIVEN blank username
        val blankUsername = " "

        // WHEN user try to login
        landingViewModel.login(blankUsername)
        val result = landingViewModel.snackBarMessage.getOrAwaitValue().peekContent()

        // THEN result display error messsage
        assertTrue(result == R.string.error_login)
    }

    @Test
    fun login_withEmptyName_resultError() {
        // GIVEN blank username
        val blankUsername = ""

        // WHEN user try to login
        landingViewModel.login(blankUsername)
        val result = landingViewModel.snackBarMessage.getOrAwaitValue().peekContent()

        // THEN result display error messsage
        assertTrue(result == R.string.error_login)
    }

    @Test
    fun login_withFilledUsername_resultSuccess() {
        // GIVEN username rachman
        val userRachman = "Rachman"

        // WHEN user try to login
        landingViewModel.login(userRachman)
        val result = landingViewModel.snackBarMessage.getOrAwaitValue().peekContent()

        // THEN result success (0)
        assertTrue(result == 0)
    }
}