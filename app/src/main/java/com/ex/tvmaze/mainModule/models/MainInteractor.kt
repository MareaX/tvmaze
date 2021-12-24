package com.ex.tvmaze.mainModule.models

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ex.tvmaze.ShowApplication
import com.ex.tvmaze.common.entities.ShowEntity
import com.ex.tvmaze.common.repository.ShowRepo
import com.ex.tvmaze.mainModule.viewModel.MainViewModel
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val mvm: MainViewModel,
    private val repo: ShowRepo
) {

    internal fun getAllShowsInternal(callback: (List<ShowEntity>) -> Unit) {
        doAsync {
            val storeList = ShowApplication.database.showDao().getAllShows()
            uiThread {
                callback(storeList)
            }
        }
    }

    internal fun setAllShows(callback: (List<ShowEntity>) -> Unit) = mvm.viewModelScope.launch {
        repo.getShows().let { response ->
            if (response.isSuccessful) {
                doAsync {
                    ShowApplication.database.showDao().insertAllShows(response.body()!!)
                }
                callback(response.body()!!)
            } else {
                Log.d("Tag", "GetShows Error: ${response.code()}")
            }
        }
    }
}