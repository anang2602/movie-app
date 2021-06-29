package com.technicalassigments.moviewapp.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.framework.mvvm.data.model.Genre
import com.technicalassigments.moviewapp.R
import com.technicalassigments.moviewapp.data.model.Movie
import com.technicalassigments.moviewapp.databinding.ListKategoriBinding
import com.technicalassigments.moviewapp.ui.main.helper.GetSelectedGenre

class GenreAdapter(private val genres: ArrayList<Genre>, private val onGetSelectedGenre: GetSelectedGenre) :
    RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListKategoriBinding) : RecyclerView.ViewHolder(binding.root) {
        private val selectedGenre = arrayListOf<Int>()
        fun onBind(genre: Genre, onGetSelectedGenre: GetSelectedGenre) {
            binding.tvGenre.text = genre.name
            if (genre.id in selectedGenre) {
                binding.llGenre.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.white)
                )
            } else {
                binding.llGenre.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.onsurface)
                )
            }
            itemView.setOnClickListener {
                if (genre.id in selectedGenre) {
                    selectedGenre.remove(genre.id)
                    binding.llGenre.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.onsurface)
                    )
                    genre.id?.let { it1 -> onGetSelectedGenre.onGetSelectedGenre(it1) }
                } else {
                    genre.id?.let { it1 -> selectedGenre.add(it1) }
                    binding.llGenre.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.white)
                    )
                    genre.id?.let { it1 -> onGetSelectedGenre.onGetSelectedGenre(it1) }
                }
            }
        }
    }

    fun addGenres(genre: List<Genre>) {
        genres.addAll(genre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListKategoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind(genres[position], onGetSelectedGenre)

    override fun getItemCount() = genres.size
}