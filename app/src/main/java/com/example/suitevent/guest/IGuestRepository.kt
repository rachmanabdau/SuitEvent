package com.example.suitevent.guest

import com.example.suitevent.model.GuestResponse
import com.example.suitevent.model.Result

interface IGuestRepository {

    suspend fun getGuest(page: Int = 1): Result<GuestResponse?>
}