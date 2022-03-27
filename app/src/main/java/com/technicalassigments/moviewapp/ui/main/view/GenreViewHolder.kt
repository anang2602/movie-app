package com.technicalassigments.moviewapp.ui.main.view

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.technicalassigments.moviewapp.ui.main.model.GenreMovieUI
import com.technicalassigments.moviewapp.R
import com.technicalassigments.moviewapp.databinding.ListKategoriItemBinding
import com.technicalassigments.moviewapp.ui.main.callback.GetSelectedGenre

class GenreViewHolder(private val binding: ListKategoriItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val selectedGenre = arrayListOf<Int>()
    fun onBind(genreUI: GenreMovieUI, onGetSelectedGenre: GetSelectedGenre) {
        binding.tvGenre.text = genreUI.name
        if (genreUI.id in selectedGenre) {
            binding.llGenre.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.white)
            )
        } else {
            binding.llGenre.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.onsurface)
            )
        }
        itemView.setOnClickListener {
            onClickGenre(genreUI, onGetSelectedGenre)
        }
    }

    private fun onClickGenre(genreUI: GenreMovieUI, onGetSelectedGenre: GetSelectedGenre) {
        if (genreUI.id in selectedGenre) {
            selectedGenre.remove(genreUI.id)
            binding.llGenre.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.onsurface)
            )
            genreUI.id.let { id ->
                onGetSelectedGenre.onGetSelectedGenre(id)
            }
        } else {
            genreUI.id.let { it1 -> selectedGenre.add(it1) }
            binding.llGenre.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.white)
            )
            genreUI.id.let { id ->
                onGetSelectedGenre.onGetSelectedGenre(id)
            }
        }
    }
}
