package com.ex.tvmaze.mainModule.adapter

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
import com.ex.tvmaze.common.entities.shows.ShowEntity
import com.ex.tvmaze.databinding.ItemShowBinding

class ShowAdapter(private var listener: OnClickListener) :
    RecyclerView.Adapter<ShowAdapter.ViewHolder>() {

    private lateinit var mContext: Context
    private val diffCallback = object : DiffUtil.ItemCallback<ShowEntity>() {
        override fun areItemsTheSame(oldItem: ShowEntity, newItem: ShowEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShowEntity, newItem: ShowEntity): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var shows: List<ShowEntity>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = ItemShowBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val show = shows[position]

        with(holder) {
            setListener(show)
            with(binding) {
                tvTitle.text = show.name
                tvDescription.text = ShowApplication.html2text(show.summary)
                tvGeneros.text = show.genres.toString()

                Glide.with(mContext)
                    .load(show.image.original)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imgPhoto)
            }
        }
    }
    override fun getItemCount(): Int = shows.size

    inner class ViewHolder(val binding: ItemShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setListener(showEntity: ShowEntity) {
            with(binding.root) {
                setOnClickListener { listener.onClick(showEntity) }
            }
        }
    }
}