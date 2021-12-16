package com.ex.tvmaze.mainModule.adapter

import com.ex.tvmaze.common.entities.shows.ShowEntity

interface OnClickListener {
    fun onClick(showEntity: ShowEntity)
}