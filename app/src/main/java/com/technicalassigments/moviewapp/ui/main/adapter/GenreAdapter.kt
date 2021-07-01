package com.technicalassigments.moviewapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technicalassigments.moviewapp.data.model.Genre
import com.technicalassigments.moviewapp.databinding.ListKategoriItemBinding
import com.technicalassigments.moviewapp.ui.main.callback.GetSelectedGenre
import com.technicalassigments.moviewapp.ui.main.view.GenreViewHolder

class GenreAdapter(
    private val genres: ArrayList<Genre>,
    private val onGetSelectedGenre: GetSelectedGenre
) :
    RecyclerView.Adapter<GenreViewHolder>() {

    fun addGenres(genre: List<Genre>) {
        genres.clear()
        genres.addAll(genre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding =
            ListKategoriItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) =
        holder.onBind(genres[position], onGetSelectedGenre)

    override fun getItemCount() = genres.size
}