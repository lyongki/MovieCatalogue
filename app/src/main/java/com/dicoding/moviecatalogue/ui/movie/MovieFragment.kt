package com.dicoding.moviecatalogue.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.core.data.Resource
import com.dicoding.moviecatalogue.core.domain.model.Movie
import com.dicoding.moviecatalogue.core.ui.MovieAdapter
import com.dicoding.moviecatalogue.databinding.FragmentMovieBinding
import com.dicoding.moviecatalogue.ui.detail.DetailFilmActivity
import com.dicoding.moviecatalogue.ui.film.FilmActivity
import com.dicoding.moviecatalogue.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val movieAdapter = MovieAdapter()
    private val viewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            showProgressBar(isTrue = true, isError = false)

            movieAdapter.onItemClick = { movie ->
                val intent = Intent(context, DetailFilmActivity::class.java)
                intent.putExtra(
                    DetailFilmActivity.EXTRA_ID,
                    movie.id.toString()
                )
                intent.putExtra(
                    DetailFilmActivity.EXTRA_FILM,
                    movie.movieId
                )
                intent.putExtra(
                    DetailFilmActivity.EXTRA_TYPE,
                    "movie"
                )
                startActivity(intent)
            }

            with(binding.rvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = movieAdapter
            }

            if (arguments?.getString(FilmActivity.SORT, "").equals(""))
                    viewModel.getMovies().observe(viewLifecycleOwner, resourceObserver)
            else {
                viewModel.getSortMovies(
                    false,
                    arguments?.getString(FilmActivity.SORT, "").toString()
                ).observe(viewLifecycleOwner, dataObserver)
            }
        }
    }

    private val resourceObserver = Observer<Resource<List<Movie>>> { movieList ->
        if (movieList != null) {
            when (movieList) {
                is Resource.Loading -> showProgressBar(isTrue = true, isError = false)
                is Resource.Error -> {
                    showProgressBar(isTrue = false, isError = true)
                    Toast.makeText(context, movieList.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    showProgressBar(isTrue = false, isError = false)
                    movieAdapter.list = movieList.body as List<Movie>
                }
            }
        }
    }

    private val dataObserver = Observer<List<Movie>> { movieList ->
        if (movieList != null) {
            showProgressBar(isTrue = false, isError = false)
            movieAdapter.list = movieList
        }
    }

    private fun showProgressBar(isTrue: Boolean, isError: Boolean) {
        with(binding) {
            when {
                isTrue && !isError -> {
                    progressBar.visibility = View.VISIBLE
                    rvMovie.visibility = View.GONE
                }
                !isTrue && isError -> progressBar.visibility = View.GONE
                else -> {
                    rvMovie.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}