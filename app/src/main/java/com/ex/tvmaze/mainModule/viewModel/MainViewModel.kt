package com.ex.tvmaze.mainModule.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.tvmaze.common.entities.shows.ShowEntity
import com.ex.tvmaze.common.repository.ShowRepo
import com.ex.tvmaze.mainModule.models.MainInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        interactor.setAllShows {
            _response.postValue(it)
        }
    }
}