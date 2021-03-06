package com.technicalassigments.moviewapp.ui.detailmovie.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technicalassigments.moviewapp.R
import com.technicalassigments.moviewapp.databinding.ListReviewsItemBinding
import com.technicalassigments.moviewapp.ui.detailmovie.model.ReviewsUI
import com.technicalassigments.moviewapp.utils.loadAvatar
import java.text.SimpleDateFormat
import java.util.*

class DetailMovieViewHolder(private val binding: ListReviewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private var isExpand = false

    fun bind(reviews: ReviewsUI) {
        binding.tvContent.text = reviews.content
        binding.tvUsername.text = reviews.author
        binding.tvCreated.text = dateFormat(reviews.created_at.toString())
        binding.ivAvatar.loadAvatar(itemView.context, reviews.author_details?.avatar_path.toString())

        binding.btnShowmore.setOnClickListener {
            expandText()
        }

    }

    private fun expandText() {
        if (isExpand) {
            binding.btnShowmore.text = itemView.context.getString(R.string.show_more)
            binding.tvContent.maxLines = 5
            isExpand = false
        } else {
            binding.btnShowmore.text = itemView.context.getString(R.string.show_less)
            binding.tvContent.maxLines = Integer.MAX_VALUE
            isExpand = true
        }
    }

    private fun dateFormat(dateString: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val sdf2 = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US)
        val date = sdf.parse(dateString)!!
        return sdf2.format(date)

    }

    companion object {

        fun create(parent: ViewGroup): DetailMovieViewHolder {
            val binding =
                ListReviewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DetailMovieViewHolder(binding)
        }
    }

}