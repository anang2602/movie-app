package com.technicalassigments.moviewapp.ui.detailmovie.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.technicalassigments.moviewapp.data.model.Movie
import com.technicalassigments.moviewapp.data.model.Reviews
import com.technicalassigments.moviewapp.ui.detailmovie.view.DetailMovieViewHolder
import com.technicalassigments.moviewapp.ui.detailmovie.viewmodel.DetailMovieViewModel
import com.technicalassigments.moviewapp.ui.main.view.MovieViewHolder

class DetailMovieAdapter : PagingDataAdapter<Reviews, DetailMovieViewHolder>(MOVIE_COMPARATOR) {

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
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Reviews>() {
            override fun areItemsTheSame(oldItem: Reviews, newItem: Reviews): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Reviews, newItem: Reviews): Boolean =
                oldItem == newItem
        }
    }

}