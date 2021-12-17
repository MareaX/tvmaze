package com.ex.tvmaze.episodesModule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ex.tvmaze.R
import com.ex.tvmaze.databinding.FragmentEpisodesBinding
import com.ex.tvmaze.episodesModule.adapter.EpisodeAdapter
import com.ex.tvmaze.episodesModule.viewModel.EpisodeViewModel
import com.ex.tvmaze.mainModule.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesFragment : Fragment() {

    private lateinit var mBinding: FragmentEpisodesBinding
    private val mEpisodeViewModel: EpisodeViewModel by viewModels()
    private var mActivity: MainActivity? = null
    private lateinit var mEpisodeAdapter: EpisodeAdapter
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mEpisodeViewModel.id = this.arguments!!.getInt(getString(R.string.SeasonEntity_id))
        mEpisodeViewModel.loadEpisodes()
        setupRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentEpisodesBinding.inflate(inflater, container, false)
        mBinding.rvEpisodes.apply {
            setHasFixedSize(true)
            layoutManager =
                GridLayoutManager(
                    mBinding.root.context,
                    resources.getInteger(R.integer.main_columns)
                )
            adapter = mEpisodeAdapter
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
    }

    private fun setupActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = this.arguments!!.getString(getString(R.string.SeasonEntity_title))
        setHasOptionsMenu(true)
    }

    private fun setupRecyclerView() {
        mEpisodeAdapter = EpisodeAdapter()
        mEpisodeViewModel.responseEpisodes.observe(this, { episodes ->
            mBinding.progressBar.visibility = if (episodes.isEmpty()) View.VISIBLE else View.GONE
            mEpisodeAdapter.episodes = episodes
            mBinding.rvEpisodes.adapter = mEpisodeAdapter
        })
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
        mActivity?.supportActionBar?.title = getString(R.string.show_info_title)
        super.onDestroy()
    }



}