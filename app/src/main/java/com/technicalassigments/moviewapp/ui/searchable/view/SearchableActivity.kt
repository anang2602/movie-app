package com.technicalassigments.moviewapp.ui.searchable.view

import android.app.SearchManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.MenuItem
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
import com.technicalassigments.moviewapp.databinding.ActivitySearchableBinding
import com.technicalassigments.moviewapp.ui.base.ViewModelFactory
import com.technicalassigments.moviewapp.ui.main.adapter.GenreAdapter
import com.technicalassigments.moviewapp.ui.main.adapter.MovieLoadStateAdapter
import com.technicalassigments.moviewapp.ui.main.viewmodel.MainViewModel
import com.technicalassigments.moviewapp.ui.searchable.adapter.SearchAdapter
import com.technicalassigments.moviewapp.ui.searchable.viewmodel.SearchViewModel
import com.technicalassigments.moviewapp.utils.RecentSuggestionsProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchableActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchableBinding
    private lateinit var searchViewModel: SearchViewModel
    private var searchAdapter = SearchAdapter()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViewModel()
        handleIntent(intent)
        initAdapter()
    }

    private fun initAdapter() {

        binding.rvMovies.adapter = searchAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { searchAdapter.retry() },
            footer = MovieLoadStateAdapter { searchAdapter.retry() }
        )
        searchAdapter.addLoadStateListener { loadState ->
            binding.rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.groupError.isVisible = loadState.source.refresh is LoadState.Error

            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && searchAdapter.itemCount == 0
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


    private fun initViewModel() {
        searchViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiService.create())
        ).get(SearchViewModel::class.java)
    }

    private fun searchMovie(query: String) {
        searchJob = lifecycleScope.launch {
            searchViewModel.getMovies(query).collect {
                searchAdapter.submitData(it)
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            handleIntent(it)
        }
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(this, RecentSuggestionsProvider.AUTHORITY, RecentSuggestionsProvider.MODE)
                    .saveRecentQuery(query, null)
                supportActionBar?.title = query
                searchMovie(query)
            }
        }
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