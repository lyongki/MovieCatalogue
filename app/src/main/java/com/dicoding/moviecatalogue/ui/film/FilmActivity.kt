package com.dicoding.moviecatalogue.ui.film

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.core.utils.SortUtils
import com.dicoding.moviecatalogue.databinding.ActivityFilmBinding
import com.google.android.material.tabs.TabLayoutMediator

class FilmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmBinding
    private lateinit var adapter: SectionPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter("")
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_favorite -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("moviecatalogue://favorite")))
            R.id.action_newest -> {
                setUpAdapter(SortUtils.NEWEST)
                item.isChecked = true
            }
            R.id.action_oldest -> {
                setUpAdapter(SortUtils.OLDEST)
                item.isChecked = true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpAdapter(sort: String) {
        with(binding){
            val lastPosition = viewPager.currentItem

            adapter = SectionPagerAdapter(sort, supportFragmentManager, lifecycle)
            viewPager.adapter = adapter

            viewPager.currentItem = lastPosition
        }
    }

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.movies,
            R.string.tvshow
        )

        const val FAVORITE = "FAVORITE"
        const val SORT = "SORT"
    }
}