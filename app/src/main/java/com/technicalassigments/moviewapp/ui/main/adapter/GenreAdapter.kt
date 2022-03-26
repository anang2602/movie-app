package com.technicalassigments.moviewapp.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technicalassigments.moviewapp.ui.main.model.GenreMovie
import com.technicalassigments.moviewapp.databinding.ListKategoriItemBinding
import com.technicalassigments.moviewapp.ui.main.callback.GetSelectedGenre
import com.technicalassigments.moviewapp.ui.main.view.GenreViewHolder

class GenreAdapter(
    private val genres: ArrayList<GenreMovie>,
    private val onGetSelectedGenre: GetSelectedGenre
) :
    RecyclerView.Adapter<GenreViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun addGenres(genre: Collection<GenreMovie>) {
        genres.clear()
        genres.addAll(genre)
        notifyDataSetChanged()
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