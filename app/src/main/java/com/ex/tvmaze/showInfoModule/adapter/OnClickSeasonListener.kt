package com.ex.tvmaze.showInfoModule.adapter

import com.ex.tvmaze.common.entities.season.SeasonEntity

interface OnClickSeasonListener {
    fun onClick(seasonEntity: SeasonEntity)
}