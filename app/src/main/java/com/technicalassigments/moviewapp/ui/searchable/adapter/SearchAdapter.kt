package com.technicalassigments.moviewapp.ui.searchable.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.technicalassigments.moviewapp.data.model.Movie
import com.technicalassigments.moviewapp.ui.main.view.MovieViewHolder
import com.technicalassigments.moviewapp.ui.searchable.view.SearchViewHolder

class SearchAdapter : PagingDataAdapter<Movie, SearchViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val movieItem = getItem(position)
        if (movieItem != null) {
            holder.bind(movieItem)
        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

}