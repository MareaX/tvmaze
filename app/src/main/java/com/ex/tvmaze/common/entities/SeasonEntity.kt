package com.ex.tvmaze.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

data class SeasonEntity(
    val id: Int = 0,
    val number: Int = 1,
    val name: String = "",
)