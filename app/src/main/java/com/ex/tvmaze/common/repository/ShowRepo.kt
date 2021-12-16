package com.ex.tvmaze.common.repository

import com.ex.tvmaze.common.api.ShowApiService
import javax.inject.Inject

class ShowRepo @Inject
constructor(private val apiService: ShowApiService) {
    suspend fun getShows() = apiService.getShows()

    suspend fun getEpisodes(id: Int) = apiService.getEpisodes(id)
}

