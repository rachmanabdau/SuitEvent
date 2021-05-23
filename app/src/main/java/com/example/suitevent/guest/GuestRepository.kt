package com.example.suitevent.guest

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.suitevent.data.remote.NetworkService
import com.example.suitevent.model.GuestResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GuestRepository @Inject constructor(
    private val networkService: NetworkService
) : IGuestRepository {

    override suspend fun getGuest(): Flow<PagingData<GuestResponse.Result>> {
        return Pager(PagingConfig(pageSize = 6, prefetchDistance = 1, enablePlaceholders = false)) {
            GuestDataSource(networkService)
        }.flow
    }
}