package com.example.suitevent.guest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.suitevent.model.GuestResponse
import com.example.suitevent.model.Result

class GuestDataSource(
    private val repo: IGuestRepository,
) : PagingSource<Int, GuestResponse.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GuestResponse.Result> {
        val nextPageNumber = params.key ?: 1
        return try {
            val guestResponse: Result<GuestResponse?> = repo.getGuest(nextPageNumber)

            if (guestResponse is Result.Success) {
                val guestList = guestResponse.data?.data
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
                LoadResult.Error(Exception(getErrorMessage(guestResponse)))
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

    private fun getErrorMessage(result: Result<*>): String {
        return if (result is Result.Error) {
            result.exception.localizedMessage ?: "Unknown exception occured."
        } else {
            "Unknown exception occured."
        }
    }
}