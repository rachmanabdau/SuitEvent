package com.example.suitevent.guest

import androidx.paging.PagingSource
import com.example.suitevent.FakeRemoteService
import com.example.suitevent.GuestDummy
import com.example.suitevent.data.remote.NetworkService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class GuestDataSourceTest {

    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var guestRepository: IGuestRepository

    @Before
    fun setupViewModel() {
        fakeRemoteSource = FakeRemoteService()
        guestRepository = GuestRepository(fakeRemoteSource)
    }

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfPageKeyedData() = runBlockingTest {
        val pagingSource = GuestDataSource(fakeRemoteSource)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = GuestDummy.guestListPage1.data,
                prevKey = null,
                nextKey = GuestDummy.guestListPage1.page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }

}