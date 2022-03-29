package com.technicalassigments.movieapp.cache.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.technicalassigments.movieapp.cache.TestCoroutineRule
import com.technicalassigments.movieapp.cache.database.MovieDatabaseTest
import com.technicalassigments.movieapp.cache.entity.GenreEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
open class GenreDaoTest: MovieDatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun insertGenreTest() = testCoroutineRule.runBlockingTest  {
        val genreEntity = listOf(GenreEntity(1, "fake name"))
        appDatabase.genreDao().insertGenres(genreEntity)
        val result = appDatabase.genreDao().fetchGenreMovie()
        Assert.assertEquals(1, result.collect { it.size })
    }



}