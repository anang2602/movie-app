package com.technicalassigments.moviewapp.ui.main.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.databinding.ListMoviesItemBinding
import com.technicalassigments.moviewapp.ui.detailmovie.view.DetailMovieActivity
import com.technicalassigments.moviewapp.ui.main.model.MovieUI
import com.technicalassigments.moviewapp.utils.load

class MovieViewHolder(private val binding: ListMoviesItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieUI) {
        binding.tvTitle.text = movie.original_title
        binding.ivPoster.load(itemView.context, "${BuildConfig.POSTER_URL}${movie.poster_path}")

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE, movie)
            itemView.context.startActivity(intent)
        }
    }

    companion object {

        const val MOVIE = "movie"

        fun create(parent: ViewGroup): MovieViewHolder {
            val binding =
                ListMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieViewHolder(binding)
        }
    }

}