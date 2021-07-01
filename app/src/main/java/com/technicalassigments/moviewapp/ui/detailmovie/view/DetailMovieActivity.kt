package com.technicalassigments.moviewapp.ui.detailmovie.view

import android.app.SearchManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicalassigments.moviewapp.BuildConfig
import com.technicalassigments.moviewapp.R
import com.technicalassigments.moviewapp.data.api.ApiService
import com.technicalassigments.moviewapp.data.model.Movie
import com.technicalassigments.moviewapp.data.model.VideoResult
import com.technicalassigments.moviewapp.databinding.ActivityDetailMovieBinding
import com.technicalassigments.moviewapp.ui.base.ViewModelFactory
import com.technicalassigments.moviewapp.ui.detailmovie.adapter.DetailMovieAdapter
import com.technicalassigments.moviewapp.ui.detailmovie.viewmodel.DetailMovieViewModel
import com.technicalassigments.moviewapp.ui.main.adapter.MovieLoadStateAdapter
import com.technicalassigments.moviewapp.ui.main.view.MovieViewHolder.Companion.MOVIE
import com.technicalassigments.moviewapp.utils.load
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private var movie: Movie? = null
    private lateinit var detailMovieViewModel: DetailMovieViewModel
    private var reviewsJob: Job? = null
    private val adapter = DetailMovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViewModel()
        setupUi()
        initVideosTrailer()
        initAdapter()
    }

    private fun fetchReviews(movie_id: Int) {
        reviewsJob?.cancel()
        reviewsJob = lifecycleScope.launch {
            detailMovieViewModel.getReviews(movie_id).collect {
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter() {
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

    private fun initVideosTrailer() {
        detailMovieViewModel.videoResult.observe(this) { result ->
            when (result) {
                is VideoResult.Success -> {
                    if (result.data.isNotEmpty()) {
                        val url = "${BuildConfig.YOUTUBE_EMBED}${result.data[0].key}"
                        binding.videoview.settings.javaScriptEnabled = true
                        binding.videoview.loadUrl(url)
                        binding.tvTrailerEmpty.visibility = View.GONE
                    } else {
                        binding.tvTrailerEmpty.visibility = View.VISIBLE
                    }

                }
                is VideoResult.Error -> {
                    Toast.makeText(this, result.error.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViewModel() {
        detailMovieViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiService.create())
        ).get(DetailMovieViewModel::class.java)
    }

    private fun setupUi() {
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
        return sdf2.format(date)

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