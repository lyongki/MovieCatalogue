package com.dicoding.moviecatalogue.ui.film

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.moviecatalogue.ui.movie.MovieFragment
import com.dicoding.moviecatalogue.ui.tvshow.TvShowFragment

class SectionPagerAdapter(
    val sort: String,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = FilmActivity.TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> {
                MovieFragment().apply {
                    arguments = Bundle().apply {
                        putString(FilmActivity.SORT, sort)
                    }
                }
            }
            1 -> {
                TvShowFragment().apply {
                    arguments = Bundle().apply {
                        putString(FilmActivity.SORT, sort)
                    }
                }
            }
            else -> Fragment()
        }

}