package com.ex.tvmaze.mainModule

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ex.tvmaze.R
import com.ex.tvmaze.ShowApplication
import com.ex.tvmaze.common.entities.ShowEntity
import com.ex.tvmaze.databinding.ActivityMainBinding
import com.ex.tvmaze.mainModule.adapter.OnClickListener
import com.ex.tvmaze.mainModule.adapter.ShowAdapter
import com.ex.tvmaze.mainModule.viewModel.MainViewModel
import com.ex.tvmaze.showInfoModule.ShowInfoFragment
import dagger.hilt.android.AndroidEntryPoint
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var mBinding: ActivityMainBinding

    private lateinit var mAdapter: ShowAdapter
    private val mMainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ShowApplication.haveInternet = ShowApplication.checkConnection(this)
        setupRecyclerView()

        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                mAdapter.filter.filter(mBinding.etSearch.text.toString())
            }
        })

    }

    override fun onStart() {
        super.onStart()
        if (!ShowApplication.haveInternet) {
            Toast.makeText(this, "Acceso sin internet", Toast.LENGTH_LONG).show()
        }
    }

private fun setupRecyclerView() {
    mAdapter = ShowAdapter(this)
    //getStores()
    mBinding.recyclerView.apply {
        setHasFixedSize(true)
        layoutManager =
            GridLayoutManager(this@MainActivity, resources.getInteger(R.integer.main_columns))
        adapter = mAdapter
    }

    mMainViewModel.responseShow.observe(this, { shows ->
        mBinding.progressBar.visibility = if (shows.isEmpty()) View.VISIBLE else View.GONE
        mAdapter.shows = shows
        mAdapter.allShows = shows
    })
}

private fun launchShowInfoFragment(showEntity: ShowEntity) {
    val data = Bundle()
    data.putInt(getString(R.string.ShowEntity_id), showEntity.id)
    data.putString(getString(R.string.ShowEntity_url), showEntity.url)
    data.putString(getString(R.string.ShowEntity_name), showEntity.name)
    data.putString(getString(R.string.ShowEntity_genres), showEntity.genres.toString())
    data.putString(getString(R.string.ShowEntity_premiered), showEntity.premiered)
    data.putFloat(getString(R.string.ShowEntity_rating), showEntity.rating.average.toFloat())
    data.putString(getString(R.string.ShowEntity_image), showEntity.image.medium)
    data.putString(
        getString(R.string.ShowEntity_summary),
        ShowApplication.html2text(showEntity.summary)
    )
    val fragment = ShowInfoFragment()
    fragment.arguments = data
    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    //showEntity
    fragmentTransaction.add(R.id.containerMain, fragment)
    fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
}

override fun onClick(showEntity: ShowEntity) {
    launchShowInfoFragment(showEntity)
}

}