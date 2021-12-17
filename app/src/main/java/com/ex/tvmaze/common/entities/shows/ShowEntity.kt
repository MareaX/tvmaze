package com.ex.tvmaze.common.entities.shows

import com.ex.tvmaze.common.entities.same.Image
import com.ex.tvmaze.common.entities.same.Links
import com.ex.tvmaze.common.entities.same.Rating

data class ShowEntity (
    val id : Int = 0,
    val url : String = "",
    val name : String = "",
    val genres : List<String> = listOf(),
    val premiered : String = "",
    val rating : Rating = Rating(0.0),
    val image : Image = Image("",""),
    val summary : String = "",
)