package com.technicalassigments.movieapp.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreEntity(
    @PrimaryKey
    var id: Int,
    var name: String
)
