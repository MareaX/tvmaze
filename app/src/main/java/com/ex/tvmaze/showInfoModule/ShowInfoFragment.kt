package com.ex.tvmaze.showInfoModule

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ex.tvmaze.R
import com.ex.tvmaze.ShowApplication
import com.ex.tvmaze.common.entities.SeasonEntity
import com.ex.tvmaze.databinding.FragmentShowInfoBinding
import com.ex.tvmaze.episodesModule.EpisodesFragment
import com.ex.tvmaze.mainModule.MainActivity
import com.ex.tvmaze.showInfoModule.adapter.OnClickSeasonListener
import com.ex.tvmaze.showInfoModule.adapter.ShowSeasonAdapter
import com.ex.tvmaze.showInfoModule.viewModel.ShowInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowInfoFragment : Fragment(), OnClickSeasonListener {

    lateinit var mBinding: FragmentShowInfoBinding
    private val mShowInfoViewModel: ShowInfoViewModel by viewModels()
    private var mActivity: MainActivity? = null
    private lateinit var mShowSeasonAdapter: ShowSeasonAdapter
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = this.arguments!!
        mShowInfoViewModel.id = bundle.getInt(getString(R.string.ShowEntity_id))
        mShowInfoViewModel.loadSeasons()
        setupRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentShowInfoBinding.inflate(inflater, container, false)
        mBinding.recyclerViewEpisodes.apply {
            setHasFixedSize(true)
            layoutManager =
                GridLayoutManager(
                    mBinding.root.context,
                    resources.getInteger(R.integer.main_columns)
                )
            adapter = mShowSeasonAdapter
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupRecyclerView() {
        mShowSeasonAdapter = ShowSeasonAdapter(this)
        //getStores()
        mShowInfoViewModel.responseSeason.observe(this, { seasons ->
            //mBinding.progressBar.visibility = if (shows.isEmpty()) View.VISIBLE else View.GONE
            mShowSeasonAdapter.seasons = seasons
            mBinding.recyclerViewEpisodes.adapter = mShowSeasonAdapter
        })
    }

    private fun setupViewModel() {
        setUiStore()
        setupActionBar()
    }

    private fun setupActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.show_info_title)
        setHasOptionsMenu(true)
    }

    @SuppressLint("SetTextI18n")
    private fun setUiStore() {
        with(mBinding) {
            tvTitle.text = bundle.getString(getString(R.string.ShowEntity_name))
            tvDescription.text = bundle.getString(getString(R.string.ShowEntity_summary))
            tvGeneros.text =
                "${getString(R.string.generos_info)} ${bundle.getString(getString(R.string.ShowEntity_genres))}"
            rating.rating = (bundle.getFloat(getString(R.string.ShowEntity_rating)) / 2)
            tvDate.text =
                "${getString(R.string.date_info)} ${bundle.getString(getString(R.string.ShowEntity_premiered))}"
            tvWebPage.text =
                "${getString(R.string.webpage_info)} ${bundle.getString(getString(R.string.ShowEntity_url))}"
            if (ShowApplication.haveInternet)
                Glide.with(this@ShowInfoFragment)
                    .load(bundle.getString(getString(R.string.ShowEntity_image)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imgPhotoInternal)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        setHasOptionsMenu(false)
        super.onDestroy()
    }

    override fun onClick(seasonEntity: SeasonEntity) {
        launchEpisodesFragment(seasonEntity)
    }

    private fun launchEpisodesFragment(seasonEntity: SeasonEntity) {
        val data = Bundle()
        data.putInt(getString(R.string.SeasonEntity_id), seasonEntity.id)
        val seasonName =
            if (seasonEntity.name.isBlank()) "Temporada ${seasonEntity.number}" else seasonEntity.name
        data.putString(getString(R.string.SeasonEntity_title), seasonName)
        val fragment = EpisodesFragment()
        fragment.arguments = data
        val fragmentManager = activity?.supportFragmentManager;
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.containerShowInfoFragment, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }
}