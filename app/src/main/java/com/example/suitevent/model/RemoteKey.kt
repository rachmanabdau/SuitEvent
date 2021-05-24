package com.example.suitevent.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guest_remote_keys")
data class RemoteKey(@PrimaryKey val nextKey: Int)
