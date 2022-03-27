package com.technicalassigments.moviewapp.ui.detailmovie.view

import android.annotation.SuppressLint
import android.app.SearchManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicalassigments.movieapp.domain.utils.Status
import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.MovieApp
import com.technicalassigments.moviewapp.R
import com.technicalassigments.moviewapp.databinding.ActivityDetailMovieBinding
import com.technicalassigments.moviewapp.ui.detailmovie.adapter.DetailMovieAdapter
import com.technicalassigments.moviewapp.ui.detailmovie.di.DaggerDetailMovieComponent
import com.technicalassigments.moviewapp.ui.detailmovie.di.DetailMovieModule
import com.technicalassigments.moviewapp.ui.detailmovie.model.ReviewsUI
import com.technicalassigments.moviewapp.ui.detailmovie.viewmodel.DetailMovieViewModel
import com.technicalassigments.moviewapp.ui.main.adapter.MovieLoadStateAdapter
import com.technicalassigments.moviewapp.ui.main.model.MovieUI
import com.technicalassigments.moviewapp.ui.main.view.MovieViewHolder.Companion.MOVIE
import com.technicalassigments.moviewapp.utils.load
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private var movie: MovieUI? = null
    private var reviewsJob: Job? = null
    private val adapter = DetailMovieAdapter()

    @Inject
    lateinit var detailMovieViewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onInitDependencyInjection()

        setupUI()
        initReviewRecyclerAdapter()
        observeVideosTrailer()
    }


    private fun onInitDependencyInjection() {
        DaggerDetailMovieComponent
            .builder()
            .networkComponent(MovieApp.networkComponent(this))
            .domainComponent(MovieApp.domainComponent(this))
            .cacheComponent(MovieApp.cacheComponent(this))
            .detailMovieModule(DetailMovieModule(this))
            .build()
            .inject(this)
    }

    private fun fetchReviews(movie_id: Int) {
        reviewsJob?.cancel()
        reviewsJob = lifecycleScope.launch {
            delay(50)
            detailMovieViewModel.getReviews(movie_id).map { pagingdata ->
                pagingdata.map {
                    ReviewsUI.ReviewResponseToReviewUI().mapFrom(it)
                }
            }.collect {
                adapter.submitData(it)
            }
        }
    }

    private fun initReviewRecyclerAdapter() {
        binding.rvReviews.layoutManager = LinearLayoutManager(this)
        binding.rvReviews.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter.retry() },
            footer = MovieLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->

            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0

            binding.tvReviewsEmpty.isVisible = isListEmpty

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(this, it.error.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = "Search for movies"
            this.findViewById<View>(androidx.appcompat.R.id.search_plate)
                .setBackgroundColor(Color.TRANSPARENT)
            maxWidth = Int.MAX_VALUE
        }
        return true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun observeVideosTrailer() {
        detailMovieViewModel.videoResult.observe(this) { resource ->
            binding.tvTrailerEmpty.isVisible = resource.data.isNullOrEmpty()
            when (resource.status) {
                Status.OFFLINE -> {}
                Status.SUCCESS -> {
                    resource.data?.let {
                        try {
                            val url = "${BuildConfig.YOUTUBE_EMBED}${it.toList()[0].key}"
                            binding.videoview.settings.javaScriptEnabled = true
                            binding.videoview.loadUrl(url)
                        } catch (e: Exception) {
                            Log.e("Error", e.localizedMessage)
                        }
                    }

                }
                Status.LOADING -> {}
                Status.ERROR -> {}
            }
        }
    }


    private fun setupUI() {
        movie = intent.getParcelableExtra(MOVIE)
        movie?.id?.let { detailMovieViewModel.getVideos(it) }
        binding.ivPoster.load(this, "${BuildConfig.POSTER_URL}${movie?.poster_path}")
        binding.tvTitle.text = movie?.title
        supportActionBar?.title = movie?.title
        binding.tvOverview.text = movie?.overview
        binding.tvRelease.text = movie?.release_date?.let { dateFormat(it) }
        binding.tvPopularity.text = "Popularity ${movie?.popularity?.toInt().toString()}"
        binding.tvVote.text =
            "Average vote ${movie?.vote_average} \t Vote count ${movie?.vote_count}"
        movie?.id?.let { fetchReviews(it) }
    }

    private fun dateFormat(dateString: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val sdf2 = SimpleDateFormat("dd MMM yyyy", Locale.US)
        val date = sdf.parse(dateString)
        return sdf2.format(date!!)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}