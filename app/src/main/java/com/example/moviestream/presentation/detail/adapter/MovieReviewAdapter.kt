package com.example.moviestream.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviestream.core.domain.model.MovieReviewItem
import com.example.moviestream.databinding.ListMovieReviewBinding
import com.example.moviestream.utils.invisible
import com.example.moviestream.utils.visible
import java.util.Locale

class MovieReviewAdapter : PagingDataAdapter<MovieReviewItem, MovieReviewAdapter.MovieReviewViewHolder>(DIFF_CALLBACK) {

    inner class MovieReviewViewHolder(private val binding: ListMovieReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieReviewItem) {
            with(binding) {
                tvUser.text = item.authorDetails.username
                tvProfPicText.text = item.authorDetails.username[0].toString().uppercase(Locale.getDefault())
                if (item.authorDetails.avatarPath != "") {
                    imgViewProfPic.visible()
                    imgViewProfPic.load("https://image.tmdb.org/t/p/w500/" + item.authorDetails.avatarPath)
                    tvProfPicText.invisible()
                }
                tvDateComment.text = item.createdAt
                tvContainReview.text = item.content
            }
        }
    }

    override fun onBindViewHolder(holder: MovieReviewAdapter.MovieReviewViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieReviewAdapter.MovieReviewViewHolder {
        val binding = ListMovieReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieReviewViewHolder(binding)
    }



    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieReviewItem>() {
            override fun areItemsTheSame(oldItem: MovieReviewItem, newItem: MovieReviewItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MovieReviewItem, newItem: MovieReviewItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}