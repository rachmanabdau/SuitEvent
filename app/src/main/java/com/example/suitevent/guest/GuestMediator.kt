package com.example.suitevent.guest

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.suitevent.data.local.SuitEventDatabase
import com.example.suitevent.data.remote.NetworkService
import com.example.suitevent.model.GuestResponse
import com.example.suitevent.model.RemoteKey

@OptIn(ExperimentalPagingApi::class)
class GuestMediator(
    private val database: SuitEventDatabase,
    private val networkService: NetworkService
) : RemoteMediator<Int, GuestResponse.Result>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GuestResponse.Result>
    ): MediatorResult {
        /*return*/
        val test = try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        database.keyDao().remoteKeyByQuery()
                    }

                    // You must explicitly check if the page key is null when
                    // appending, since null is only valid for initial load.
                    // If you receive null for APPEND, that means you have
                    // reached the end of pagination and there are no more
                    // items to load.
                    if (remoteKey.nextKey == 0) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.nextKey
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = networkService.getGuestsList(
                page = loadKey ?: 1
            ).await()
            val responseData = response.body()?.data

            if (response.isSuccessful && responseData != null) {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.eventDao().clearAllFavouriteMovie()
                        database.keyDao().deleteByQuery()
                    }

                    // Update RemoteKey for this query.
                    database.keyDao().insertOrReplace(
                        RemoteKey(response.body()?.page?.plus(1) ?: 0)
                    )

                    // Insert new users into database, which invalidates the
                    // current PagingData, allowing Paging to present the updates
                    // in the DB.
                    database.eventDao().insertAllGuest(responseData)
                }

                MediatorResult.Success(
                    endOfPaginationReached = response.body()?.page == (response.body()?.totalPages
                        ?: 0)
                )
            } else {
                val error = Exception("Something went wrong.")
                MediatorResult.Error(error)
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
        return test
    }

}