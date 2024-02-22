package com.example.moviestream.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviestream.databinding.FragmentMovieReviewBinding
import com.example.moviestream.presentation.detail.adapter.MovieReviewAdapter
import com.example.moviestream.utils.LoadingStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieReviewFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMovieReviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var movieReviewAdapter: MovieReviewAdapter
    private var movieId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieId = arguments?.getString("movieId").toString()
        Log.d("TAG", "movieId: $movieId")

        initializeView()
        getListMovieReview(movieId)

        binding.imgClose.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun getListMovieReview(movieId: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getListMovieReview(movieId).collectLatest {
                movieReviewAdapter.submitData(it)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initializeView() {
        binding.apply {
            movieReviewAdapter = MovieReviewAdapter()
            rvMovieReview.adapter = movieReviewAdapter.withLoadStateFooter(
                footer =  LoadingStateAdapter { movieReviewAdapter.retry() }
            )
            rvMovieReview.setHasFixedSize(true)
            btnErrorLoad.setOnClickListener { movieReviewAdapter.retry() }
            rvMovieReview.layoutManager = LinearLayoutManager(requireContext())
            movieReviewAdapter.addLoadStateListener { load ->
                pbMovieList.isVisible = load.source.refresh is LoadState.Loading
                tvErrorLoad.isVisible = load.source.refresh is LoadState.Error
                btnErrorLoad.isVisible = load.refresh is LoadState.Error
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}