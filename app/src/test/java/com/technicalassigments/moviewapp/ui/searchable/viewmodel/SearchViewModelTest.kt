package com.technicalassigments.moviewapp.ui.searchable.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.technicalassigments.movieapp.domain.usecase.FetchMovieUseCaseImpl
import com.technicalassigments.moviewapp.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.OverrideMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    lateinit var fetchMovieUseCaseImpl: FetchMovieUseCaseImpl

    @OverrideMockKs
    lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `should fetch movie by query from use case`() {
        // given
        val query = "fakeQuery"
        // when
        viewModel.getMovies(query)
        //then
        coVerify { fetchMovieUseCaseImpl.fetchMovieByQuery(query) }
    }

}