package com.technicalassigments.moviewapp.ui.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.data.model.Movie
import com.technicalassigments.moviewapp.databinding.ListMoviesBinding
import com.technicalassigments.moviewapp.utils.load

class MovieViewHolder(private val binding: ListMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.tvTitle.text = movie.original_title
        binding.ivPoster.load(itemView.context, "${BuildConfig.POSTER_URL}${movie.poster_path}")
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val binding =
                ListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieViewHolder(binding)
        }
    }

}