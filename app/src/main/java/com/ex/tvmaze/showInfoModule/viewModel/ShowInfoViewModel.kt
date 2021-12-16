package com.ex.tvmaze.showInfoModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ex.tvmaze.common.entities.episodes.EpisodeEntity
import com.ex.tvmaze.common.entities.shows.ShowEntity
import com.ex.tvmaze.common.repository.ShowRepo
import com.ex.tvmaze.showInfoModule.model.ShowInfoInteractor
import javax.inject.Inject

class ShowInfoViewModel @Inject constructor(private val repo: ShowRepo) : ViewModel() {

    private var show = ShowEntity()
    internal val showSelected = MutableLiveData<ShowEntity>()

    fun setStoreSelected(showEntity: ShowEntity){
        showSelected.value = showEntity
    }

    fun getStoreSelected():LiveData<ShowEntity>{
        return  showSelected
    }


    private var interactor: ShowInfoInteractor = ShowInfoInteractor()//(this, repo)
    private var _response = MutableLiveData<List<EpisodeEntity>>()

    val responseShow: LiveData<List<EpisodeEntity>> get() = _response

    init {
        loadEpisodes()
    }

    private fun loadEpisodes() {
        /*interactor.setAllShows {
            _response.postValue(it)
        }*/
    }
}