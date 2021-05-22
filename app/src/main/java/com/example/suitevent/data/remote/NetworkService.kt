package com.example.suitevent.data.remote

import com.example.suitevent.model.GuestResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("users")
    fun getGuestsList(
        @Query("page") page: Int
    ): Deferred<Response<GuestResponse>>
}