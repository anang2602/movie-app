package com.technicalassigments.moviewapp.ui.detailmovie.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.technicalassigments.moviewapp.ui.detailmovie.view.ReviewsLoadStateViewHolder
import com.technicalassigments.moviewapp.ui.main.view.MovieLoadStateViewHolder

class ReviewsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ReviewsLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: ReviewsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ReviewsLoadStateViewHolder {
        return ReviewsLoadStateViewHolder.create(parent, retry)
    }
}