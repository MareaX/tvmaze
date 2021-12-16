package com.ex.tvmaze.mainModule.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ex.tvmaze.common.entities.shows.ShowEntity
import com.ex.tvmaze.common.repository.ShowRepo
import com.ex.tvmaze.mainModule.viewModel.MainViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainInteractor @Inject constructor(private val mvm: MainViewModel, private val repo: ShowRepo) {

    internal fun setAllShows(callback: (List<ShowEntity>) -> Unit) = mvm.viewModelScope.launch {
        repo.getShows().let { response ->
            if (response.isSuccessful){
                callback(response.body()!!)
            } else{
                Log.d("Tag","GetShows Error: ${response.code()}")
            }
        }
    }
}