package com.technicalassigments.movieapp.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.technicalassigments.movieapp.cache.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Insert(onConflict = REPLACE)
    fun insertGenres(genreEntity: Collection<GenreEntity>)

    @Query("SELECT * FROM genreentity")
    fun fetchGenreMovie(): Flow<List<GenreEntity>>

    @Query("DELETE FROM genreentity")
    fun clearGenres()

}