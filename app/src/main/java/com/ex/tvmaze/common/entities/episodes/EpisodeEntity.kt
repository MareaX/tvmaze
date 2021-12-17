package com.ex.tvmaze.common.entities.episodes

import com.ex.tvmaze.common.entities.same.Image
import com.ex.tvmaze.common.entities.same.Links
import com.ex.tvmaze.common.entities.same.Rating

data class EpisodeEntity (
	val id : Int = 0,
	val name : String = "",
	val season : Int = 0,
	val number : Int = 0,
	val summary : String = "",
	val image: Image = Image("","")
)