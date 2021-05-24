package com.example.suitevent.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.suitevent.model.GuestResponse

@Dao
interface EventDao {

    @Query("SELECT * FROM guest_table")
    fun getAllGuest(): PagingSource<Int, GuestResponse.Result>

    @Query("SELECT * FROM guest_table WHERE first_name LIKE :name OR lastName LIKE :name")
    fun getGuesByName(name: String): PagingSource<Int, GuestResponse.Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGuest(guests: List<GuestResponse.Result>)

    @Query("DELETE FROM guest_table")
    suspend fun clearAllFavouriteMovie()
}