package com.technicalassigments.moviewapp.ui.main.view

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicalassigments.moviewapp.R
import com.technicalassigments.moviewapp.data.api.ApiService
import com.technicalassigments.moviewapp.data.model.GenreResult
import com.technicalassigments.moviewapp.databinding.ActivityMainBinding
import com.technicalassigments.moviewapp.ui.base.ViewModelFactory
import com.technicalassigments.moviewapp.ui.main.adapter.GenreAdapter
import com.technicalassigments.moviewapp.ui.main.adapter.MovieAdapter
import com.technicalassigments.moviewapp.ui.main.adapter.MovieLoadStateAdapter
import com.technicalassigments.moviewapp.ui.main.callback.GetSelectedGenre
import com.technicalassigments.moviewapp.ui.main.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), GetSelectedGenre, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var mainViewModel: MainViewModel
    private val movieAdapter = MovieAdapter()
    private var selectedGenre = ArrayList<Int>()
    private var moviesJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupUI()
        initViewModel()
        initAdapter()
        fetchMovies(selectedGenre.joinToString())
    }

    private fun fetchMovies(genre: String) {
        moviesJob?.cancel()
        moviesJob = lifecycleScope.launch {
            mainViewModel.getMovies(genre).collect {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun initAdapter() {
        binding.rvGenre.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        genreAdapter = GenreAdapter(arrayListOf(), this)
        binding.rvGenre.adapter = genreAdapter
        binding.rvGenre.visibility = View.VISIBLE
        mainViewModel.genreResult.observe(this) { result ->
            when (result) {
                is GenreResult.Success -> {
                    genreAdapter.addGenres(result.data)
                    genreAdapter.notifyDataSetChanged()
                }
                is GenreResult.Error -> {
                    showError(result.error.message.toString())
                }
            }
        }
        binding.rvMovies.adapter = movieAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { movieAdapter.retry() },
            footer = MovieLoadStateAdapter { movieAdapter.retry() }
        )
        movieAdapter.addLoadStateListener { loadState ->
            binding.rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.groupError.isVisible = loadState.source.refresh is LoadState.Error

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
            binding.tvError.text = getText(R.string.no_results)
            binding.ivError.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_movie_24)
            )
            binding.groupError.visibility = View.VISIBLE
            binding.rvMovies.visibility = View.GONE
        } else {
            binding.groupError.visibility = View.GONE
            binding.rvMovies.visibility = View.VISIBLE
        }
    }

    private fun showError(msg: String) {
        binding.progressBar.visibility = View.GONE
        binding.tvError.text = msg
        binding.ivError.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.ic_baseline_error_24)
        )
        binding.groupError.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.GONE
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiService.create())
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        binding.btnRetry.setOnClickListener(this)
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
                .collect { binding.rvMovies.scrollToPosition(0) }
        }
    }
}