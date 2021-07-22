package com.dicoding.moviecatalogue.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.core.data.Resource
import com.dicoding.moviecatalogue.core.domain.model.Movie
import com.dicoding.moviecatalogue.core.domain.model.TvShow
import com.dicoding.moviecatalogue.databinding.ActivityDetailFilmBinding
import com.dicoding.moviecatalogue.viewmodel.DetailFilmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailFilmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFilmBinding
    private val viewModel: DetailFilmViewModel by viewModel()
    private var menu: Menu? = null

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_FILM = "extra_FILM"
        const val EXTRA_TYPE = "extra_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showProgressBar(isTrue = true, isError = false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu

        val id = intent.getStringExtra(EXTRA_ID).toString()
        val film = intent.getStringExtra(EXTRA_FILM).toString()

        if (intent.getStringExtra(EXTRA_TYPE).toString() == "movie") {
            viewModel.getMovie(id, film)
                .observe(this, movieObserver)
        } else {
            viewModel.getTvShow(id, film)
                .observe(this, tvShowObserver)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                if (intent.getStringExtra(EXTRA_TYPE).toString() == "movie") {
                    viewModel.movie.favorited = !viewModel.movie.favorited
                    val movie = viewModel.movie
                    viewModel.updateMovie()
                    updateUI(movie.favorited, item)
                } else {
                    viewModel.tvShow.favorited = !viewModel.tvShow.favorited
                    val tvShow = viewModel.tvShow
                    viewModel.updateTvShow()
                    updateUI(tvShow.favorited, item)
                }
            }

            R.id.action_comment -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("moviecatalogue://comment"))
                intent.putExtra(EXTRA_TYPE, getIntent().getStringExtra(EXTRA_TYPE))
                intent.putExtra(EXTRA_FILM, getIntent().getStringExtra(EXTRA_FILM))
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val movieObserver = Observer<Resource<Movie>> {
        when (it) {
            is Resource.Success -> {
                val movie = it.body as Movie
                addMovieToView(movie)
                updateUI(movie.favorited, menu?.findItem(R.id.action_favorite))
                viewModel.movie = movie
                showProgressBar(isTrue = false, isError = false)
            }
            is Resource.Error -> {
                showProgressBar(isTrue = false, isError = true)
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> showProgressBar(isTrue = true, isError = false)
        }

    }

    private val tvShowObserver = Observer<Resource<TvShow>> {
        when (it) {
            is Resource.Success -> {
                val tvShow = it.body as TvShow
                viewModel.tvShow = tvShow
                addTvShowToView(tvShow)
                updateUI(tvShow.favorited, menu?.findItem(R.id.action_favorite))
                showProgressBar(isTrue = false, isError = false)
            }
            is Resource.Error -> {
                showProgressBar(isTrue = false, isError = true)
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> showProgressBar(isTrue = true, isError = false)
        }

    }

    private fun addTvShowToView(it: TvShow) {
        supportActionBar?.title = it.title
        with(binding) {
            tvTitle.text = getString(R.string.title_year, it.title, it.year)
            tvOverview.text = it.overview
            tvProduction.text = it.productionCompanies
            tvGenre.text = it.genre
            tvDuration.text = it.duration
            tvRating.text = it.rating

            Glide.with(this@DetailFilmActivity as Context)
                .load(it.imagePath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(ivPoster)
        }
    }

    private fun addMovieToView(it: Movie) {
        supportActionBar?.title = it.title
        with(binding) {
            tvTitle.text = getString(R.string.title_year, it.title, it.year)
            tvOverview.text = it.overview
            tvProduction.text = it.productionCompanies
            tvGenre.text = it.genre
            tvDuration.text = it.duration
            tvRating.text = it.rating

            Glide.with(this@DetailFilmActivity as Context)
                .load(it.imagePath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(ivPoster)
        }
    }

    private fun showProgressBar(isTrue: Boolean, isError: Boolean) {
        with(binding) {
            when {
                isTrue && !isError -> {
                    progressBar.visibility = View.VISIBLE
                    svDetailFilm.visibility = View.GONE
                }
                !isTrue && isError -> progressBar.visibility = View.GONE
                else -> {
                    svDetailFilm.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun updateUI(favorited: Boolean, item: MenuItem?) {
        if (favorited) item?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        else item?.icon = ContextCompat.getDrawable(this, R.drawable.ic_unfavorite)
    }
}