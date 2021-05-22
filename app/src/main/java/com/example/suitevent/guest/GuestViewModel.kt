package com.example.suitevent.guest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.suitevent.model.GuestResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuestViewModel @Inject constructor(private val guestRepo: GuestRepository) : ViewModel() {

    private val _guestResult = MutableLiveData<PagingData<GuestResponse.Result>>()
    val guestResult: LiveData<PagingData<GuestResponse.Result>> = _guestResult

    fun getGuestList() {
        viewModelScope.launch {
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 6, prefetchDistance = 1)
            ) {
                GuestDataSource(guestRepo)
            }.flow
                .cachedIn(this).collectLatest {
                    _guestResult.value = it
                }
        }
    }
}