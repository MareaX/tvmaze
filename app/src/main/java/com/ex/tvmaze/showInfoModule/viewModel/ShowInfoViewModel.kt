package com.ex.tvmaze.showInfoModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ex.tvmaze.ShowApplication
import com.ex.tvmaze.common.entities.SeasonEntity
import com.ex.tvmaze.common.repository.ShowRepo
import com.ex.tvmaze.showInfoModule.model.ShowInfoInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowInfoViewModel @Inject constructor(private val repo: ShowRepo) : ViewModel() {

    private var idSeason = 0
    var id: Int
        get() = idSeason
        set(newId) {
            idSeason = newId
        }

    private var interactor: ShowInfoInteractor = ShowInfoInteractor(this, repo)
    private var _response = MutableLiveData<List<SeasonEntity>>()

    val responseSeason: LiveData<List<SeasonEntity>> get() = _response

    internal fun loadSeasons() {
        if (ShowApplication.haveInternet) {
            interactor.setAllSeasons(id, {
                _response.postValue(it)
            })
        }
    }
}