package com.example.moviestream.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.moviestream.R
import com.example.moviestream.core.domain.model.MovieDetail
import com.example.moviestream.databinding.FragmentMovieDetailBinding
import com.example.moviestream.utils.gone
import com.example.moviestream.utils.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModels()
    private var movieId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        movieId = arguments?.getString("movieId").toString()
        Log.d("TAG", "movieId: $movieId")

        viewModel.getMovieDetail(movieId)

        binding.tvSeeReview.setOnClickListener {
            val action = MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieReviewFragment(movieId)
            findNavController().navigate(action)
        }
    }

    private fun observer() {
        viewModel.movieDetail.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> movieDetailUiState(state) }
            .launchIn(lifecycleScope)
    }

    private fun movieDetailUiState(state: MovieDetailUiState) {
        when (state) {
            is MovieDetailUiState.Loading -> handleLoadDetail(state.loading)
            is MovieDetailUiState.Success -> initializeData(state.movieDetail)
            is MovieDetailUiState.Error -> Snackbar.make(
                requireView(),
                state.message,
                Snackbar.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initializeData(movieDetail: MovieDetail) {
        binding.apply {
            tvMovieTitle.text = movieDetail.title
            tvRatingGenre.text =
                "${movieDetail.releaseDate} | ${movieDetail.voteAverage} | ${movieDetail.status}"
            tvDesc.text = movieDetail.overview
            ivPosterBackdrop.load( "https://image.tmdb.org/t/p/w500/" + movieDetail.posterPath) {
                crossfade(500)
                placeholder(R.drawable.ic_image_placeholder_filled)
            }
        }
    }

    private fun handleLoadDetail(isLoading: Boolean) {
        when (isLoading) {
            true -> binding.pbDetailScreen.visible()
            false -> binding.pbDetailScreen.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}