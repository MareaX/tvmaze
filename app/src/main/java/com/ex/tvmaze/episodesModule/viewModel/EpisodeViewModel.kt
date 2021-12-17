package com.ex.tvmaze.episodesModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ex.tvmaze.common.entities.episodes.EpisodeEntity
import com.ex.tvmaze.common.entities.season.SeasonEntity
import com.ex.tvmaze.common.repository.ShowRepo
import com.ex.tvmaze.episodesModule.models.EpisodeInteractor
import com.ex.tvmaze.showInfoModule.model.ShowInfoInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val repo: ShowRepo) : ViewModel() {

    private var idSeason = 0
    var id: Int get() = idSeason
        set(newId) {
            idSeason = newId
        }

    private var interactor: EpisodeInteractor = EpisodeInteractor(this, repo)
    private var _response = MutableLiveData<List<EpisodeEntity>>()

    val responseEpisodes: LiveData<List<EpisodeEntity>> get() = _response


    internal fun loadEpisodes() {
        interactor.setAllEpisodes(id,{
            _response.postValue(it)
        })
    }
}