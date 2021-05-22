package com.example.suitevent.model


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class GuestResponse(
    @Json(name = "data")
    val `data`: List<Result>,
    @Json(name = "page")
    val page: Int,
    @Json(name = "per_page")
    val perPage: Int,
    @Json(name = "support")
    val support: Support,
    @Json(name = "total")
    val total: Int,
    @Json(name = "total_pages")
    val totalPages: Int
) {

    @Parcelize
    data class Result(
        @Json(name = "avatar")
        val avatar: String,
        @Json(name = "email")
        val email: String,
        @Json(name = "first_name")
        val firstName: String,
        @Json(name = "id")
        val id: Int,
        @Json(name = "last_name")
        val lastName: String
    ) : Parcelable

    data class Support(
        @Json(name = "text")
        val text: String,
        @Json(name = "url")
        val url: String
    )
}