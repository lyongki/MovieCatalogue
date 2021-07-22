package com.dicoding.moviecatalogue.ui.tvshow

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
import com.dicoding.moviecatalogue.core.domain.model.TvShow
import com.dicoding.moviecatalogue.core.ui.TvShowAdapter
import com.dicoding.moviecatalogue.databinding.FragmentTvShowBinding
import com.dicoding.moviecatalogue.ui.detail.DetailFilmActivity
import com.dicoding.moviecatalogue.ui.film.FilmActivity
import com.dicoding.moviecatalogue.viewmodel.TvShowViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!
    private val tvShowAdapter = TvShowAdapter()
    private val viewModel: TvShowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            showProgressBar(isTrue = true, isError = false)

            tvShowAdapter.onItemClick = {tvShow ->
                val intent = Intent(context, DetailFilmActivity::class.java)
                intent.putExtra(
                    DetailFilmActivity.EXTRA_ID,
                    tvShow.id.toString()
                )
                intent.putExtra(
                    DetailFilmActivity.EXTRA_FILM,
                    tvShow.tvShowId
                )
                intent.putExtra(
                    DetailFilmActivity.EXTRA_TYPE,
                    "tvshow"
                )
                startActivity(intent)
            }

            with(binding.rvTvshow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = tvShowAdapter
            }

            if (arguments?.getString(FilmActivity.SORT, "").equals("")) {
                    viewModel.getTvShows().observe(viewLifecycleOwner, resourceObserver)
            } else {
                viewModel.getSortTvShows(
                    false,
                    arguments?.getString(FilmActivity.SORT, "").toString()
                ).observe(viewLifecycleOwner, dataObserver)
            }
        }
    }

    private val resourceObserver = Observer<Resource<List<TvShow>>> { tvShowList ->
        if (tvShowList != null) {
            when (tvShowList) {
                is Resource.Loading -> showProgressBar(isTrue = true, isError = false)
                is Resource.Error -> {
                    showProgressBar(isTrue = false, isError = true)
                    Toast.makeText(context, tvShowList.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    showProgressBar(isTrue = false, isError = false)
                    if (tvShowList.body != null)
                        tvShowAdapter.list = tvShowList.body as List<TvShow>
                }
            }
        }
    }

    private val dataObserver = Observer<List<TvShow>> { tvShowList ->
        if (tvShowList != null) {
            showProgressBar(isTrue = false, isError = false)
            tvShowAdapter.list = tvShowList
        }
    }

    private fun showProgressBar(isTrue: Boolean, isError: Boolean) {
        with(binding) {
            when {
                isTrue && !isError -> {
                    progressBar.visibility = View.VISIBLE
                    rvTvshow.visibility = View.GONE
                }
                !isTrue && isError -> progressBar.visibility = View.GONE
                else -> {
                    rvTvshow.visibility = View.VISIBLE
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