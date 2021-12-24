package com.ex.tvmaze.common.api

import com.ex.tvmaze.common.entities.EpisodeEntity
import com.ex.tvmaze.common.entities.SeasonEntity
import com.ex.tvmaze.common.entities.ShowEntity
import com.ex.tvmaze.common.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ShowApiService {
    @GET(Constants.END_POINT)
    suspend fun getShows():Response<List<ShowEntity>>

    @GET(Constants.END_POINT + Constants.END_POINT_SEASONS)
    suspend fun getSeasons(@Path("id") idShow:Int):Response<List<SeasonEntity>>

    @GET(Constants.END_POINT_EPISODES)
    suspend fun getEpisodes(@Path("id") idSeason:Int):Response<List<EpisodeEntity>>

}