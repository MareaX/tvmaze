package com.ex.tvmaze.common.entities.episodes

data class EpisodeEntity (

	val id : Int,
	val url : String,
	val name : String,
	val season : Int,
	val number : Int,
	val type : String,
	val airdate : String,
	val airtime : String,
	val airstamp : String,
	val runtime : Int,
	val rating : Rating,
	val image : Image,
	val summary : String,
	val links : Links
)