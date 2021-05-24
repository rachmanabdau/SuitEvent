package com.example.suitevent.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.suitevent.model.GuestResponse
import com.example.suitevent.model.RemoteKey

@Database(
    entities = arrayOf(
        GuestResponse.Result::class, RemoteKey::class
    ), version = 1
)
abstract class SuitEventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun keyDao(): RemoteKeyDao
}
