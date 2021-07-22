package com.dicoding.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.core.R
import com.dicoding.moviecatalogue.core.databinding.ItemsTvshowBinding
import com.dicoding.moviecatalogue.core.domain.model.TvShow

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {
    var list: List<TvShow> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClick: ((TvShow) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemTvShowBinding =
            ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemTvShowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(private val binding: ItemsTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow) {
            with(binding) {
                tvTitle.text = itemView.context.getString(R.string.title_year, tvShow.title, tvShow.year)
                tvOverview.text = tvShow.overview
                tvRating.text = tvShow.rating
                itemView.setOnClickListener {
                    onItemClick?.invoke(tvShow)
                }

                com.bumptech.glide.Glide.with(itemView.context)
                    .load(tvShow.imagePath)
                    .apply {
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        RequestOptions.overrideOf(120, 120)
                    }
                    .error(R.drawable.ic_error).into(ivPoster)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}