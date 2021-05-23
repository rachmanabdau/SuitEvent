package com.example.suitevent.guest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.suitevent.data.remote.NetworkService
import com.example.suitevent.model.GuestResponse

class GuestDataSource(
    private val networkService: NetworkService,
) : PagingSource<Int, GuestResponse.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GuestResponse.Result> {
        val nextPageNumber = params.key ?: 1
        return try {
            val guestResponse = networkService.getGuestsList(nextPageNumber).await()

            if (guestResponse.isSuccessful) {
                val guestList = guestResponse.body()?.data
                if (guestList != null && guestList.isNotEmpty()) {
                    LoadResult.Page(
                        data = guestList,
                        prevKey = null,
                        nextKey = nextPageNumber + 1
                    )
                } else {
                    // prevent loading empty list
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }
            } else {
                LoadResult.Error(Exception("Something went wrong."))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GuestResponse.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}