package com.dicoding.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.core.R
import com.dicoding.moviecatalogue.core.databinding.ItemsMovieBinding
import com.dicoding.moviecatalogue.core.domain.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    var list: List<Movie> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemMovieBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                tvTitle.text = itemView.context.getString(R.string.title_year, movie.title, movie.year)
                tvOverview.text = movie.overview
                tvRating.text = movie.rating

                Glide.with(itemView.context)
                    .load(movie.imagePath)
                    .apply {
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        RequestOptions.overrideOf(120, 120)
                    }
                    .error(R.drawable.ic_error).into(ivPoster)
            }
            itemView.setOnClickListener { onItemClick?.invoke(movie) }
        }
    }

    override fun getItemCount(): Int =
        list.size
}