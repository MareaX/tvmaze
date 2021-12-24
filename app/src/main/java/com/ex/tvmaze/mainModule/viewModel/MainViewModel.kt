package com.ex.tvmaze.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ex.tvmaze.ShowApplication
import com.ex.tvmaze.common.entities.ShowEntity
import com.ex.tvmaze.common.repository.ShowRepo
import com.ex.tvmaze.mainModule.models.MainInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: ShowRepo) : ViewModel() {

    private var interactor: MainInteractor = MainInteractor(this, repo)
    private var _response = MutableLiveData<List<ShowEntity>>()
    val responseShow: LiveData<List<ShowEntity>> get() = _response

    init {
        loadShows()
    }

    private fun loadShows(){
        if (ShowApplication.haveInternet) {
            interactor.setAllShows {
                _response.postValue(it)
            }
        } else {
            interactor.getAllShowsInternal {
                _response.postValue(it)
            }
        }
    }
}