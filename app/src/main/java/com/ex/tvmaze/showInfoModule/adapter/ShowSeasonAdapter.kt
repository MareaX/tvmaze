package com.ex.tvmaze.showInfoModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ex.tvmaze.common.entities.SeasonEntity
import com.ex.tvmaze.databinding.ItemSeasonBinding

class ShowSeasonAdapter(private var listener: OnClickSeasonListener) :
    RecyclerView.Adapter<ShowSeasonAdapter.ViewHolder>() {

    private lateinit var mContext: Context
    private val diffCallback = object : DiffUtil.ItemCallback<SeasonEntity>() {
        override fun areItemsTheSame(oldItem: SeasonEntity, newItem: SeasonEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SeasonEntity, newItem: SeasonEntity): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var seasons: List<SeasonEntity>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = ItemSeasonBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val season = seasons[position]

        with(holder) {
            setListener(season)
            with(binding) {
                tvNumber.text = season.number.toString()
                tvSeasonName.text =
                    if (season.name.isBlank()) "Temporada ${season.number}" else season.name
            }
        }
    }

    override fun getItemCount(): Int = seasons.size

    inner class ViewHolder(val binding: ItemSeasonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setListener(seasonEntity: SeasonEntity) {
            with(binding.root) {
                setOnClickListener { listener.onClick(seasonEntity) }
            }
        }
    }
}