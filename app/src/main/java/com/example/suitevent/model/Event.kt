package com.example.suitevent.model

import androidx.annotation.DrawableRes
import com.example.suitevent.R

data class Event(
    val id: Int,
    val name: String,
    val description: String,
    val date: String,
    @DrawableRes val image: Int = R.drawable.ic_dev_summit
)

val dummyEventList = listOf(
    Event(1, "Android Developer Summit 2018", "Lorem ipsum lorem ipsum lorem ipsum", "Oct 8, 2018"),
    Event(2, "Android Developer Summit 2019", "Lorem ipsum lorem ipsum lorem ipsum", "Oct 8, 2019"),
    Event(3, "Android Developer Summit 2020", "Lorem ipsum lorem ipsum lorem ipsum", "Oct 8, 2020"),
    Event(4, "Android Developer Summit 2021", "Lorem ipsum lorem ipsum lorem ipsum", "Oct 8, 2021"),
)
