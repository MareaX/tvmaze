package com.ex.tvmaze.showInfoModule.model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ex.tvmaze.common.entities.SeasonEntity
import com.ex.tvmaze.common.repository.ShowRepo
import com.ex.tvmaze.showInfoModule.viewModel.ShowInfoViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShowInfoInteractor@Inject constructor(private val sivm: ShowInfoViewModel, private val repo: ShowRepo) {

    internal fun setAllSeasons(id:Int, callback: (List<SeasonEntity>) -> Unit) = sivm.viewModelScope.launch {
        repo.getSeasons(id).let { response ->
            if (response.isSuccessful){
                callback(response.body()!!)
            } else{
                Log.d("Tag","GetShows Error: ${response.code()}")
            }
        }
    }
}