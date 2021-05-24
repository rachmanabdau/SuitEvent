package com.example.suitevent.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.example.suitevent.R
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: Int,
    val name: String,
    val description: String,
    val date: String,
    val coordinateLocation: LatLng,
    @DrawableRes val image: Int = R.drawable.ic_dev_summit
) : Parcelable

val dummyEventList = listOf(
    Event(
        1,
        "Android Developer Summit 2018",
        "Lorem ipsum lorem ipsum lorem ipsum",
        "Oct 8, 2018",
        LatLng(-6.889391615696547, 107.61701481164108)
    ),
    Event(
        2,
        "Android Developer Summit 2019",
        "Lorem ipsum lorem ipsum lorem ipsum",
        "Oct 8, 2019",
        LatLng(-6.895076558571646, 107.61343198419978)
    ),
    Event(
        3,
        "Android Developer Summit 2020",
        "Lorem ipsum lorem ipsum lorem ipsum",
        "Oct 8, 2020",
        LatLng(-7.708190420676736, 110.35816817593796)
    ),
    Event(
        4,
        "Android Developer Summit 2021",
        "Lorem ipsum lorem ipsum lorem ipsum",
        "Oct 8, 2021",
        LatLng(-6.216836302521338, 106.82081415890468)
    ),
)
