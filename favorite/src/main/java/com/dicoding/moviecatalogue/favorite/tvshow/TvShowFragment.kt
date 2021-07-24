package com.dicoding.moviecatalogue.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.core.domain.model.TvShow
import com.dicoding.moviecatalogue.core.ui.TvShowAdapter
import com.dicoding.moviecatalogue.databinding.FragmentTvShowBinding
import com.dicoding.moviecatalogue.favorite.film.FavoriteActivity
import com.dicoding.moviecatalogue.ui.detail.DetailFilmActivity
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

            if (arguments?.getString(FavoriteActivity.SORT, "").equals("")) {
                    viewModel.getFavoriteTvShows()
                        .observe(viewLifecycleOwner, dataObserver)
            } else {
                viewModel.getSortTvShows(
                    true,
                    arguments?.getString(FavoriteActivity.SORT, "").toString()
                ).observe(viewLifecycleOwner, dataObserver)
            }
        }
    }

    private val dataObserver = Observer<List<TvShow>> { tvShowList ->
        if (tvShowList.isNotEmpty()) {
            showProgressBar(isTrue = false, isError = false)
            tvShowAdapter.list = tvShowList
        }else{
            showProgressBar(isTrue = false, isError = true)
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvTvshow.visibility = View.GONE
        }
    }

    private fun showProgressBar(isTrue: Boolean, isError: Boolean) {
        with(binding) {
            when {
                isTrue && !isError -> {
                    progressBar.visibility = View.VISIBLE
                    rvTvshow.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }
                !isTrue && isError -> progressBar.visibility = View.GONE
                else -> {
                    rvTvshow.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}