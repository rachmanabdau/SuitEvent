package com.example.suitevent.guest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.suitevent.model.GuestResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class GuestViewModel @Inject constructor(private val guestRepo: GuestRepository) : ViewModel() {

    private val _guestResult = MutableLiveData<PagingData<GuestResponse.Result>>()
    val guestResult: LiveData<PagingData<GuestResponse.Result>> = _guestResult

    fun getGuestList() {
        viewModelScope.launch {
            guestRepo.getGuest()
                .cachedIn(viewModelScope)
                .collectLatest {
                    _guestResult.value = it
                }
        }
    }
}