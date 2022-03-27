package com.technicalassigments.moviewapp.ui.main.view

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicalassigments.movieapp.domain.utils.Status
import com.technicalassigments.moviewapp.MovieApp
import com.technicalassigments.moviewapp.R
import com.technicalassigments.moviewapp.databinding.ActivityMainBinding
import com.technicalassigments.moviewapp.ui.main.adapter.GenreAdapter
import com.technicalassigments.moviewapp.ui.main.adapter.MovieAdapter
import com.technicalassigments.moviewapp.ui.main.adapter.MovieLoadStateAdapter
import com.technicalassigments.moviewapp.ui.main.callback.GetSelectedGenre
import com.technicalassigments.moviewapp.ui.main.di.DaggerMainComponent
import com.technicalassigments.moviewapp.ui.main.di.MainModule
import com.technicalassigments.moviewapp.ui.main.model.GenreMovieUI
import com.technicalassigments.moviewapp.ui.main.model.MovieUI
import com.technicalassigments.moviewapp.ui.main.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), GetSelectedGenre, View.OnClickListener {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var genreAdapter: GenreAdapter
    private val movieAdapter = MovieAdapter()
    private var selectedGenre = ArrayList<Int>()
    private var moviesJob: Job? = null

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolbar)

        onInitDependencyInjection()
        setupListener()
        initRecyclerAdapter()
        observeGenre()
        fetchMovies(selectedGenre.joinToString())
    }

    private fun observeGenre() {
        viewModel.fetchGenre.observe(this) { resource ->
            resource.data?.let {
                genreAdapter.addGenres(
                    GenreMovieUI.GenreEntityToGenreMovie().mapFrom(it)
                )
            }
            when (resource.status) {
                Status.LOADING -> {}
                Status.OFFLINE -> {
                    showError("Offline")
                }
                Status.SUCCESS -> {}
                Status.ERROR -> {
                    resource.message?.let { showError(it) }
                }
            }
        }
    }

    private fun onInitDependencyInjection() {
        DaggerMainComponent
            .builder()
            .networkComponent(MovieApp.networkComponent(this))
            .cacheComponent(MovieApp.cacheComponent(this))
            .domainComponent(MovieApp.domainComponent(this))
            .mainModule(MainModule(this))
            .build()
            .inject(this)
    }

    private fun fetchMovies(genre: String) {
        moviesJob?.cancel()
        moviesJob = lifecycleScope.launch {
            delay(50)
            viewModel.getMovies(genre).map { pagingdata ->
                pagingdata.map { MovieUI.MovieResponseToMovie().mapFrom(it) }
            }.collect {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerAdapter() {
        viewBinding.rvGenre.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        genreAdapter = GenreAdapter(arrayListOf(), this)
        viewBinding.rvGenre.adapter = genreAdapter
        viewBinding.rvGenre.visibility = View.VISIBLE

        viewBinding.rvMovies.adapter = movieAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { movieAdapter.retry() },
            footer = MovieLoadStateAdapter { movieAdapter.retry() }
        )
        movieAdapter.addLoadStateListener { loadState ->
            viewBinding.rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
            viewBinding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            viewBinding.groupError.isVisible = loadState.source.refresh is LoadState.Error

            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && movieAdapter.itemCount == 0
            showEmptyList(isListEmpty)

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                showError(it.error.message.toString())
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            viewBinding.tvError.text = getText(R.string.no_results)
            viewBinding.ivError.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_movie_24)
            )
            viewBinding.groupError.visibility = View.VISIBLE
            viewBinding.rvMovies.visibility = View.GONE
        } else {
            viewBinding.groupError.visibility = View.GONE
            viewBinding.rvMovies.visibility = View.VISIBLE
        }
    }

    private fun showError(msg: String) {
        viewBinding.progressBar.visibility = View.GONE
        viewBinding.tvError.text = msg
        viewBinding.ivError.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.ic_baseline_error_24)
        )
        viewBinding.groupError.visibility = View.VISIBLE
        viewBinding.rvMovies.visibility = View.GONE
    }

    private fun setupListener() {
        viewBinding.btnRetry.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = "Search for movies"
            this.findViewById<View>(androidx.appcompat.R.id.search_plate)
                .setBackgroundColor(Color.TRANSPARENT)
            maxWidth = Int.MAX_VALUE
        }
        return true
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btn_retry -> {
                fetchMovies(selectedGenre.joinToString())
            }
        }
    }


    override fun onGetSelectedGenre(selected: Int) {
        if (selected in selectedGenre) {
            selectedGenre.remove(selected)
        } else {
            selectedGenre.add(selected)
        }

        fetchMovies(selectedGenre.joinToString())
        lifecycleScope.launch {
            movieAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { viewBinding.rvMovies.scrollToPosition(0) }
        }
    }
}