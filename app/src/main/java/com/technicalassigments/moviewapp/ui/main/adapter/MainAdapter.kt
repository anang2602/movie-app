package com.technicalassigments.moviewapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.data.model.Movie
import com.technicalassigments.moviewapp.databinding.ListMoviesBinding
import com.technicalassigments.moviewapp.utils.load

class MainAdapter(private val movies: ArrayList<Movie>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var currPage = 1
    private var lastPage = 1

    class ViewHolder(private val binding: ListMoviesBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(movie: Movie) {
            binding.ivPoster.load(itemView.context, "${BuildConfig.POSTER_URL}${movie.poster_path}")
            binding.tvTitle.text = movie.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind(movies[position])

    override fun getItemCount() = movies.size

    fun addMovies(movie: List<Movie>) {
        movies.addAll(movie)
    }

    fun setCurrentPage(page: Int) {
        currPage = page
    }

    fun getCurrentPage(): Int {
        return currPage
    }

    fun setLastPage(page: Int) {
        lastPage = page
    }

    fun getLastPage(page: Int): Int {
        return lastPage
    }

}