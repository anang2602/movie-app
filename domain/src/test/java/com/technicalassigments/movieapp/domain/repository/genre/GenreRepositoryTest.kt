package com.technicalassigments.movieapp.domain.repository.genre

import com.technicalassigments.movieapp.cache.dao.GenreDao
import com.technicalassigments.movieapp.domain.TestCoroutineRule
import com.technicalassigments.movieapp.domain.utils.NetworkUtils
import com.technicalassigments.movieapp.network.dto.GenreResponse
import com.technicalassigments.movieapp.network.services.GenreServices
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class GenreRepositoryTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val coroutineDispatcher = TestCoroutineDispatcher()

    private val networkUtils = mockk<NetworkUtils>(relaxed = true)

    private val genreServices = mockk<GenreServices>(relaxed = true)

    private val genreDao = mockk<GenreDao>(relaxed = true)

    private val genreRepository = GenreRepository(
        networkUtils, genreServices, genreDao, coroutineDispatcher
    )

    @Test
    fun `should check the internet calls its service and save the data to db`() =
        testCoroutineRule.runBlockingTest {
            // given
            val fakeResult = GenreResponse(
                listOf(GenreResponse.Genre(1, "fake genre"))
            )
            every { networkUtils.checkForInternet() } returns true
            coEvery { genreServices.getMoviesGenre() } returns Response.success(fakeResult)

            // when
            genreRepository.fetchGenreMovie().toList()

            // verify
            coVerifyOrder {
                // first try to get data from the db
                genreDao.fetchGenreMovie()

                // check is internet avaiable
                networkUtils.checkForInternet()

                // fetch data from the api
                genreServices.getMoviesGenre()

                // clear the database
                genreDao.clearGenres()

                // insert into the db
                genreDao.insertGenres(any())

                // return inserted
                genreDao.fetchGenreMovie()
            }

        }


}