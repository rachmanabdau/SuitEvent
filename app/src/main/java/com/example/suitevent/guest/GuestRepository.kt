package com.example.suitevent.guest

import com.example.suitevent.data.remote.NetworkService
import com.example.suitevent.model.GuestResponse
import com.example.suitevent.model.Result
import com.squareup.moshi.Moshi
import javax.inject.Inject

class GuestRepository @Inject constructor(
    private val networkService: NetworkService,
    private val moshi: Moshi
) : IGuestRepository {

    override suspend fun getGuest(page: Int): Result<GuestResponse?> {
        return try {
            val result = networkService.getGuestsList(page).await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                val errorMessage = Exception("Unknown error occured")
                Result.Error(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }
}