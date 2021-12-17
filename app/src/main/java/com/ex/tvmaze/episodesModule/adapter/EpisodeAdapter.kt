package com.ex.tvmaze.episodesModule.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ex.tvmaze.R
import com.ex.tvmaze.ShowApplication
import com.ex.tvmaze.common.entities.episodes.EpisodeEntity
import com.ex.tvmaze.databinding.ItemEpisodeBinding

class EpisodeAdapter() : RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {

    private lateinit var mContext: Context
    private val diffCallback = object : DiffUtil.ItemCallback<EpisodeEntity>() {
        override fun areItemsTheSame(oldItem: EpisodeEntity, newItem: EpisodeEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodeEntity, newItem: EpisodeEntity): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var episodes: List<EpisodeEntity>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = ItemEpisodeBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episode = episodes[position]

        with(holder) {
            with(binding) {
                tvTitleEpisode.text = episode.name
                tvSeason.text =  "${getString(holder, R.string.episode_season)} ${episode.season}"
                tvEpisodeNumber.text = if (episode.number != 0)
                    "${getString(holder, R.string.episode_number)} ${episode.number}" else "${getString(holder, R.string.episode_number)} Especial"
                tvDescriptionEpisode.text = ShowApplication.html2text(episode.summary)
                if (episode.image !== null )
                    Glide.with(mContext)
                        .load(episode.image.original)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(imgPhotoEpisode)
            }
        }
    }

    private fun getString(holder: ViewHolder, number: Int): String {
        return holder.binding.root.context.getString(number)
    }

    override fun getItemCount(): Int = episodes.size

    inner class ViewHolder(val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}