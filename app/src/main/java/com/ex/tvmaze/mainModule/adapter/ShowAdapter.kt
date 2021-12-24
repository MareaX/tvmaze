package com.ex.tvmaze.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ex.tvmaze.common.entities.ShowEntity
import com.ex.tvmaze.databinding.ItemShowBinding


class ShowAdapter(private var listener: OnClickListener) :
    RecyclerView.Adapter<ShowAdapter.ViewHolder>(), Filterable {

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
    private val differAll = AsyncListDiffer(this, diffCallback)

    var allShows: List<ShowEntity>
        get() = differAll.currentList
        set(value) {
            differAll.submitList(value)
        }
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
            bind(show)
        }
    }

    override fun getItemCount(): Int = shows.size

    inner class ViewHolder(private val binding: ItemShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: ShowEntity){
            with(binding) {
                tvTitle.text = show.name
                tvDescription.text = com.ex.tvmaze.ShowApplication.html2text(show.summary)
                tvGeneros.text = show.genres.toString()

                Glide.with(mContext)
                    .load(show.image.original)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imgPhoto)
            }
        }

         fun setListener(showEntity: ShowEntity) {
            with(binding.root) {
                setOnClickListener { listener.onClick(showEntity) }
            }
        }
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<ShowEntity>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(allShows)
            } else {
                for (item in allShows) {
                    if (item.name.lowercase().startsWith(constraint.toString().lowercase())) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            shows = filterResults?.values as List<ShowEntity>
        }

    }


}