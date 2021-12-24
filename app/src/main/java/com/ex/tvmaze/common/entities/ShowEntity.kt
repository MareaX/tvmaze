package com.ex.tvmaze.common.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ex.tvmaze.common.entities.same.Image
import com.ex.tvmaze.common.entities.same.Rating
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ShowEntity")
data class ShowEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val url: String,
    val name: String,
    val genres: List<String>,
    val premiered: String,
    @Embedded val rating: Rating,
    @Embedded val image: Image,
    val summary: String,
) {
     constructor() : this(0, "", "", listOf(), "", Rating(0.0), Image("",""), "")
}