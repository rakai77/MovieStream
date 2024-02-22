package com.example.moviestream.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviestream.R
import com.example.moviestream.core.domain.model.MovieItem
import com.example.moviestream.databinding.ListMovieBinding

class MoviePagingAdapter : PagingDataAdapter<MovieItem, MoviePagingAdapter.MoviePageViewHolder>(DIFF_CALLBACK) {
    inner class MoviePageViewHolder(private val binding: ListMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieItem) {
            with(binding) {
                tvMovieTitle.text = item.title
                imgMoviePoster.load("https://image.tmdb.org/t/p/w500/" + item.posterPath){
                    crossfade(800)
                    placeholder(R.drawable.ic_image_placeholder_filled)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MoviePagingAdapter.MoviePageViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviePagingAdapter.MoviePageViewHolder {
        val binding = ListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviePageViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}