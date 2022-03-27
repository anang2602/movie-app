package com.technicalassigments.moviewapp.ui.searchable.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.technicalassigments.moviewapp.ui.main.model.MovieUI
import com.technicalassigments.moviewapp.ui.searchable.view.SearchViewHolder

class SearchAdapter : PagingDataAdapter<MovieUI, SearchViewHolder>(MOVIE_COMPARATOR) {

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
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieUI>() {
            override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean =
                oldItem == newItem
        }
    }

}