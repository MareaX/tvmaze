package com.ex.tvmaze.serieInfoModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ex.tvmaze.databinding.FragmentSerieInfoBinding


class SerieInfoFragment : Fragment() {

    lateinit var mBinding: FragmentSerieInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSerieInfoBinding.inflate(inflater,container,false)
        return mBinding.root
    }
}