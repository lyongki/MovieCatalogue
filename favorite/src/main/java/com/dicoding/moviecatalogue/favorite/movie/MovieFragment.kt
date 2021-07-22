package com.dicoding.moviecatalogue.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.core.domain.model.Movie
import com.dicoding.moviecatalogue.core.ui.MovieAdapter
import com.dicoding.moviecatalogue.databinding.FragmentMovieBinding
import com.dicoding.moviecatalogue.favorite.film.FilmActivity
import com.dicoding.moviecatalogue.ui.detail.DetailFilmActivity
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
                    viewModel.getFavoriteMovies().observe(viewLifecycleOwner, dataObserver)
            else {
                viewModel.getSortMovies(
                    true,
                    arguments?.getString(FilmActivity.SORT, "").toString()
                ).observe(viewLifecycleOwner, dataObserver)
            }
        }
    }

    private val dataObserver = Observer<List<Movie>> { movieList ->
        if (movieList.isNotEmpty()) {
            showProgressBar(isTrue = false, isError = false)
            movieAdapter.list = movieList
        }else{
            showProgressBar(isTrue = false, isError = true)
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvMovie.visibility = View.GONE
        }

    }

    private fun showProgressBar(isTrue: Boolean, isError: Boolean) {
        with(binding) {
            when {
                isTrue && !isError -> {
                    progressBar.visibility = View.VISIBLE
                    rvMovie.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }
                !isTrue && isError -> progressBar.visibility = View.GONE
                else -> {
                    rvMovie.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.GONE
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