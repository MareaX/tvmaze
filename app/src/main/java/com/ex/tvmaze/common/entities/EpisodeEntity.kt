package com.ex.tvmaze.common.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ex.tvmaze.common.entities.same.Image
import com.ex.tvmaze.common.entities.same.Links
import com.ex.tvmaze.common.entities.same.Rating

data class EpisodeEntity (
	val id : Int,
	val name : String,
	val season : Int,
	val number : Int,
	val summary : String,
	val image: Image = Image("","")
){
	constructor() : this(0,"",0,0,"",Image("",""))
}