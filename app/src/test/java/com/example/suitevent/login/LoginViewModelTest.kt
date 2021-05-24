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

    @Test
    fun checkPalindrome_withBlankText_resultfailed() {
        // GIVEN blank palindrome
        val palindrome = " "

        // WHEN check palindrome
        landingViewModel.checkPalinDrome(palindrome)
        val result = landingViewModel.isPalindrome.getOrAwaitValue().peekContent()

        // THEN result is message palindrome blank
        assertTrue(result == R.string.palindrome_blank)
    }

    @Test
    fun checkPalindrome_withEmptyText_resultfailed() {
        // GIVEN empty palindrome
        val palindrome = ""

        // WHEN user check palindrome
        landingViewModel.checkPalinDrome(palindrome)
        val result = landingViewModel.isPalindrome.getOrAwaitValue().peekContent()

        // THEN result is palindrome blank
        assertTrue(result == R.string.palindrome_blank)
    }

    @Test
    fun checkPalindrome_withNotPalindrome_resultFailed() {
        // GIVEN text palindrome with odd length text
        val palindrome = "suitmedia"

        // WHEN user check palindrome
        landingViewModel.checkPalinDrome(palindrome)
        val result = landingViewModel.isPalindrome.getOrAwaitValue().peekContent()

        // THEN result is palindrome
        assertTrue(result == R.string.not_palindrome)
    }

    @Test
    fun checkPalindrome_withOddPalindromeLengt_resultSuccess() {
        // GIVEN text palindrome with odd length text
        val palindrome = "put it up"

        // WHEN user check palindrome
        landingViewModel.checkPalinDrome(palindrome)
        val result = landingViewModel.isPalindrome.getOrAwaitValue().peekContent()

        // THEN result is palindrome
        assertTrue(result == R.string.is_palindrome)
    }

    @Test
    fun checkPalindrome_withEventPalindromeLengt_resultSuccess() {
        // GIVEN text palindrome with odd length text
        val palindrome = "kasur rusak"

        // WHEN user check palindrome
        landingViewModel.checkPalinDrome(palindrome)
        val result = landingViewModel.isPalindrome.getOrAwaitValue().peekContent()

        // THEN result is palindrome
        assertTrue(result == R.string.is_palindrome)
    }
}