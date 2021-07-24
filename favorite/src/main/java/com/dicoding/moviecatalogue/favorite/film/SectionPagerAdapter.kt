package com.dicoding.moviecatalogue.favorite.film

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.moviecatalogue.favorite.movie.MovieFragment
import com.dicoding.moviecatalogue.favorite.tvshow.TvShowFragment

class SectionPagerAdapter(
    val sort: String,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = FavoriteActivity.TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> {
                MovieFragment().apply {
                    arguments = Bundle().apply {
                        putString(FavoriteActivity.SORT, sort)
                    }
                }
            }
            1 -> {
                TvShowFragment().apply {
                    arguments = Bundle().apply {
                        putString(FavoriteActivity.SORT, sort)
                    }
                }
            }
            else -> Fragment()
        }

}