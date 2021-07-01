package com.technicalassigments.moviewapp.ui.searchable.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.data.model.Movie
import com.technicalassigments.moviewapp.databinding.ListMoviesItemBinding
import com.technicalassigments.moviewapp.ui.detailmovie.view.DetailMovieActivity
import com.technicalassigments.moviewapp.ui.main.view.MovieViewHolder
import com.technicalassigments.moviewapp.utils.load

class SearchViewHolder(private val binding: ListMoviesItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.tvTitle.text = movie.original_title
        binding.ivPoster.load(itemView.context, "${BuildConfig.POSTER_URL}${movie.poster_path}")

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailMovieActivity::class.java)
            intent.putExtra(MovieViewHolder.MOVIE, movie)
            itemView.context.startActivity(intent)
        }
    }

    companion object {
        fun create(parent: ViewGroup): SearchViewHolder {
            val binding =
                ListMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SearchViewHolder(binding)
        }
    }

}