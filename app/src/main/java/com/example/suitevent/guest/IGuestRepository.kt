package com.example.suitevent.guest

import androidx.paging.PagingData
import com.example.suitevent.model.GuestResponse
import kotlinx.coroutines.flow.Flow

interface IGuestRepository {

    suspend fun getGuest(): Flow<PagingData<GuestResponse.Result>>
}