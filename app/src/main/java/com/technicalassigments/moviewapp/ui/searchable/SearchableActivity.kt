package com.technicalassigments.moviewapp.ui.searchable

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
import com.technicalassigments.moviewapp.R
import com.technicalassigments.moviewapp.databinding.ActivitySearchableBinding
import com.technicalassigments.moviewapp.utils.RecentSuggestionsProvider

class SearchableActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchableBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        handleIntent(intent)
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
                Toast.makeText(this, "you searched $query", Toast.LENGTH_SHORT).show()
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