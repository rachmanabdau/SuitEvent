package com.example.suitevent.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.suitevent.model.RemoteKey

@Dao
interface RemoteKeyDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrReplace(remoteKey: RemoteKey)

  @Query("SELECT * FROM guest_remote_keys")
  suspend fun remoteKeyByQuery(): RemoteKey

  @Query("DELETE FROM guest_remote_keys")
  suspend fun deleteByQuery()
}
