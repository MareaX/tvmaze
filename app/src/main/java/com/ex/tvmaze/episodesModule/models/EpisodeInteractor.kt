package com.ex.tvmaze.episodesModule.models

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ex.tvmaze.common.entities.EpisodeEntity
import com.ex.tvmaze.common.repository.ShowRepo
import com.ex.tvmaze.episodesModule.viewModel.EpisodeViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeInteractor@Inject constructor(private val evm: EpisodeViewModel, private val repo: ShowRepo) {

    internal fun setAllEpisodes(id:Int, callback: (List<EpisodeEntity>) -> Unit) = evm.viewModelScope.launch {
        repo.getEpisodes(id).let { response ->
            if (response.isSuccessful){
                callback(response.body()!!)
            } else{
                Log.d("Tag","GetShows Error: ${response.code()}")
            }
        }
    }
}