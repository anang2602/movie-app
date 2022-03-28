package com.technicalassigments.moviewapp.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.technicalassigments.movieapp.cache.entity.GenreEntity
import com.technicalassigments.movieapp.domain.usecase.FetchGenreUseCaseImpl
import com.technicalassigments.movieapp.domain.usecase.FetchMovieUseCaseImpl
import com.technicalassigments.movieapp.domain.usecase.FetchReviewsUseCaseImpl
import com.technicalassigments.movieapp.domain.utils.Resource
import com.technicalassigments.moviewapp.TestCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.OverrideMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    lateinit var observer: Observer<Resource<Collection<GenreEntity>>>

    @RelaxedMockK
    lateinit var fetchGenreUseCaseImpl: FetchGenreUseCaseImpl

    @RelaxedMockK
    lateinit var fetchMovieUseCaseImpl: FetchMovieUseCaseImpl

    @OverrideMockKs
    lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `should fetch genre from use case`() = testCoroutineRule.runBlockingTest {
        // given
        val sampleData = listOf(GenreEntity(1, "fakename"))
        val expected = flow { emit(Resource.success(sampleData)) }
        coEvery { fetchGenreUseCaseImpl.retrieveGenre() } answers { expected }
        // when
        viewModel.fetchGenre.observeForever(observer)

        //then
        coVerify(exactly = 1) { fetchGenreUseCaseImpl.retrieveGenre() }
        verify { observer.onChanged(Resource.success(sampleData)) }
        confirmVerified(fetchGenreUseCaseImpl, observer)
    }

    @Test
    fun `should fetch movie by genre from use case`() {
        // given
        val fakeGenre = "fakeGenre"
        // when
        viewModel.getMovies(fakeGenre)
        //then
        coVerify { fetchMovieUseCaseImpl.fetchMovieByGenre(fakeGenre) }
    }

}