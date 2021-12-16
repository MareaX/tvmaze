package com.ex.tvmaze.showInfoModule

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ex.tvmaze.R
import com.ex.tvmaze.common.entities.shows.ShowEntity
import com.ex.tvmaze.databinding.FragmentShowInfoBinding
import com.ex.tvmaze.mainModule.MainActivity
import com.ex.tvmaze.showInfoModule.viewModel.ShowInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowInfoFragment : Fragment() {

    lateinit var mBinding: FragmentShowInfoBinding
    private val mShowInfoViewModel: ShowInfoViewModel by viewModels()
    private var mActivity: MainActivity? = null
    private lateinit var mShowEntity: ShowEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentShowInfoBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
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
        var bundle = this.arguments
        if (bundle != null) {
            with(mBinding) {
                tvTitle.text = bundle.getString(getString(R.string.ShowEntity_name))
                tvDescription.text = bundle.getString(getString(R.string.ShowEntity_summary))
                tvGeneros.text = "${getString(R.string.generos_info)} ${bundle.getString(getString(R.string.ShowEntity_genres))}"
                rating.rating = (bundle.getFloat(getString(R.string.ShowEntity_rating))/2)
                tvDate.text = "${getString(R.string.date_info)} ${bundle.getString(getString(R.string.ShowEntity_premiered))}"
                tvWebPage.text = "${getString(R.string.webpage_info)} ${bundle.getString(getString(R.string.ShowEntity_url))}"
                Glide.with(this@ShowInfoFragment)
                    .load(bundle.getString(getString(R.string.ShowEntity_image)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imgPhotoInternal)
            }
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


}