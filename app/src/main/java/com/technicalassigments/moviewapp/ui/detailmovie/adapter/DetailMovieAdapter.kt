package com.technicalassigments.moviewapp.ui.detailmovie.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.technicalassigments.moviewapp.ui.detailmovie.model.ReviewsUI
import com.technicalassigments.moviewapp.ui.detailmovie.view.DetailMovieViewHolder

class DetailMovieAdapter : PagingDataAdapter<ReviewsUI, DetailMovieViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailMovieViewHolder {
        return DetailMovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DetailMovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        if (movieItem != null) {
            holder.bind(movieItem)
        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<ReviewsUI>() {
            override fun areItemsTheSame(oldItem: ReviewsUI, newItem: ReviewsUI): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ReviewsUI, newItem: ReviewsUI): Boolean =
                oldItem == newItem
        }
    }

}