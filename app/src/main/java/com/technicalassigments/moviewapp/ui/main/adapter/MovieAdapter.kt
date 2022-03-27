package com.technicalassigments.moviewapp.ui.main.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.technicalassigments.moviewapp.ui.main.model.MovieUI
import com.technicalassigments.moviewapp.ui.main.view.MovieViewHolder

class MovieAdapter : PagingDataAdapter<MovieUI, MovieViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        if (movieItem != null) {
            holder.bind(movieItem)
        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieUI>() {
            override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean =
                oldItem == newItem
        }
    }

}