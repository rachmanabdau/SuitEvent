package com.example.suitevent.guest

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.suitevent.data.local.SuitEventDatabase
import com.example.suitevent.data.remote.NetworkService
import com.example.suitevent.model.GuestResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class GuestRepository @Inject constructor(
    private val networkService: NetworkService,
    private val database: SuitEventDatabase
) : IGuestRepository {

    override suspend fun getGuest(): Flow<PagingData<GuestResponse.Result>> {
        return Pager(
            config = PagingConfig(pageSize = 6, prefetchDistance = 1, enablePlaceholders = false),
            remoteMediator = GuestMediator(database, networkService)
        ) {
            database.eventDao().getAllGuest()
        }.flow
    }
}