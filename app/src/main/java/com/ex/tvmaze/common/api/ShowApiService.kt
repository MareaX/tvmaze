package com.ex.tvmaze.common.api

import com.ex.tvmaze.common.entities.episodes.EpisodeEntity
import com.ex.tvmaze.common.entities.shows.ShowEntity
import com.ex.tvmaze.common.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ShowApiService {
    @GET(Constants.END_POINT)
    suspend fun getShows():Response<List<ShowEntity>>

    @GET(Constants.END_POINT + Constants.END_POINT_EPISODES)
    suspend fun getEpisodes(@Path("id") id:Int):Response<List<EpisodeEntity>>

}