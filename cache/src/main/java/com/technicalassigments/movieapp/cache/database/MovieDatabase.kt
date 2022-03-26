package com.technicalassigments.movieapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.technicalassigments.movieapp.cache.database.dao.GenreDao
import com.technicalassigments.movieapp.cache.entity.GenreEntity

@Database(
    entities = [
        GenreEntity::class
    ],
    exportSchema = false,
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun genreDao(): GenreDao

}