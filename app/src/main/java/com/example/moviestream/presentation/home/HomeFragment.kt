package com.example.moviestream.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviestream.core.domain.model.MovieGenre
import com.example.moviestream.databinding.FragmentHomeBinding
import com.example.moviestream.presentation.home.adapter.MovieGenreAdapter
import com.example.moviestream.presentation.home.adapter.MoviePagingAdapter
import com.example.moviestream.utils.LoadingStateAdapter
import com.example.moviestream.utils.gone
import com.example.moviestream.utils.invisible
import com.example.moviestream.utils.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var movieGenreAdapter: MovieGenreAdapter
    private lateinit var listMovieAdapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        initializeView()
    }

    private fun observer() {
        viewModel.movieGenre.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> movieGenreState(state) }
            .launchIn(lifecycleScope)
    }

    private fun initializeView() {
        binding.apply {
            movieGenreAdapter = MovieGenreAdapter()
            rvMovieGenre.adapter = movieGenreAdapter
            rvMovieGenre.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            listMovieAdapter = MoviePagingAdapter()
            rvMovieList.adapter = listMovieAdapter.withLoadStateFooter(
                footer =  LoadingStateAdapter { listMovieAdapter.retry() }
            )
            rvMovieList.setHasFixedSize(true)
            btnErrorLoad.setOnClickListener { listMovieAdapter.retry() }
            rvMovieList.layoutManager = LinearLayoutManager(requireContext())
            listMovieAdapter.addLoadStateListener { load ->
                pbMovieList.isVisible = load.source.refresh is LoadState.Loading
                tvErrorLoad.isVisible = load.source.refresh is LoadState.Error
                btnErrorLoad.isVisible = load.refresh is LoadState.Error
            }
        }
    }

    private fun movieGenreState(state: HomeUiState) {
        when (state) {
            is HomeUiState.Loading -> loadingMovieGenre(state.loading)
            is HomeUiState.SuccessGenreMovie -> initializeData(state.genre)
            is HomeUiState.Error -> Snackbar.make(requireView(), state.message, Snackbar.LENGTH_LONG).show()
            else -> Unit
        }
    }

    private fun initializeData(genre: MovieGenre) {
        if (genre.genres.isNotEmpty()) {
            binding.rvMovieGenre.visible()
            movieGenreAdapter.submitList(genre.genres)

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getMovieByGenre(genre.genres[0].id.toString()).collectLatest {
                    listMovieAdapter.submitData(it)
                }
            }
        } else {
            binding.rvMovieGenre.gone()
        }
    }

    private fun loadingMovieGenre(isLoading: Boolean) {
        when(isLoading) {
            true -> {
                binding.shimmerMovieGenre.root.visible()
                binding.rvMovieGenre.invisible()
            }
            false -> {
                binding.shimmerMovieGenre.root.gone()
                binding.rvMovieGenre.visible()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}