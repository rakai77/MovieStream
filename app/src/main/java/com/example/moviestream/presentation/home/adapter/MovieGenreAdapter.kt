package com.example.moviestream.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviestream.R
import com.example.moviestream.core.domain.model.MovieGenreItem
import com.example.moviestream.databinding.ListGenreMovieBinding

class MovieGenreAdapter(
    private val onItemClicked: (MovieGenreItem) -> Unit,
    private val context: Context
) : ListAdapter<MovieGenreItem, MovieGenreAdapter.MovieGenreViewHolder>(DIFF_CALLBACK) {

    private var selectedItem: MovieGenreItem? = null

    fun setSelectedItem(item: MovieGenreItem) {
        selectedItem = item
    }

    inner class MovieGenreViewHolder(private val binding: ListGenreMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieGenreItem) {
            with(binding) {
                tvMovieGenre.text = item.name
                if (selectedItem == item) {
                    cardViewGenre.isChecked = true
                    tvMovieGenre.setTextColor(ContextCompat.getColor(context, R.color.purple))
                } else {
                    cardViewGenre.isChecked = false
                    tvMovieGenre.setTextColor(ContextCompat.getColor(context, R.color.neutral_main))
                }
                itemView.setOnClickListener {
                    onItemClicked.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieGenreAdapter.MovieGenreViewHolder {
        val binding = ListGenreMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieGenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieGenreAdapter.MovieGenreViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieGenreItem>() {
            override fun areItemsTheSame(
                oldItem: MovieGenreItem,
                newItem: MovieGenreItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MovieGenreItem,
                newItem: MovieGenreItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}