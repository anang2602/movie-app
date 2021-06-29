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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.mindorks.framework.mvvm.data.model.GenreResponse
import com.technicalassigments.moviewapp.R
import com.technicalassigments.moviewapp.data.api.ApiHelper
import com.technicalassigments.moviewapp.data.api.ApiServiceImpl
import com.technicalassigments.moviewapp.databinding.ActivityMainBinding
import com.technicalassigments.moviewapp.ui.base.ViewModelFactory
import com.technicalassigments.moviewapp.ui.main.adapter.GenreAdapter
import com.technicalassigments.moviewapp.ui.main.helper.GetSelectedGenre
import com.technicalassigments.moviewapp.ui.main.viewmodel.MainViewModel
import com.technicalassigments.moviewapp.utils.Connectivity
import com.technicalassigments.moviewapp.utils.Status

class MainActivity : AppCompatActivity(), GetSelectedGenre, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var mainViewModel: MainViewModel
    private val connectivity = Connectivity()
    private var selectedGenre = ArrayList<Int>()
    private var isGenreExpanded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupUI()
        setupViewModel()
        setupCategoriesObserver()
        fetchGenre()
    }

    private fun fetchGenre() {
        if (connectivity.isOnline(this)) {
            binding.groupError.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            mainViewModel.getCategories()
        } else {
            binding.tvError.text = "Network not avaiable"
            binding.ivError.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_wifi_off_24)
            )
            binding.groupError.visibility = View.VISIBLE
        }
    }

    private fun setupCategoriesObserver() {
        mainViewModel.getCategories().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> renderGenreList(data) }
                }
                Status.LOADING -> {}
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun renderGenreList(genres: GenreResponse) {
        genres.genres?.let { genreAdapter.addGenres(it) }
        genreAdapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl.create()))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        binding.rvGenre.layoutManager = GridLayoutManager(this, 3)
        genreAdapter = GenreAdapter(arrayListOf(), this)
        binding.rvGenre.adapter = genreAdapter
        binding.ivExpandmore.setOnClickListener(this)
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
            R.id.iv_expandmore -> {
                if (isGenreExpanded) {
                    binding.cardCategories.visibility = View.VISIBLE
                    binding.rvGenre.visibility = View.GONE
                    binding.ivExpandmore.setImageDrawable(
                        ContextCompat.getDrawable(this, R.drawable.ic_baseline_expand_more_24)
                    )
                    isGenreExpanded = false
                } else {
                    binding.cardCategories.visibility = View.GONE
                    binding.rvGenre.visibility = View.VISIBLE
                    binding.ivExpandmore.setImageDrawable(
                        ContextCompat.getDrawable(this, R.drawable.ic_baseline_expand_less_24)
                    )
                    isGenreExpanded = true
                }
            }
            R.id.btn_retry -> {
                fetchGenre()
            }
        }
    }

    override fun onGetSelectedGenre(selected: Int) {
        if (selected in selectedGenre) {
            selectedGenre.remove(selected)
        } else {
            selectedGenre.add(selected)
        }
        if (selectedGenre.size > 0) {
            binding.tvCategories.text = "${selectedGenre.size} Selected"
        } else {
            binding.tvCategories.text = "All Genres"
        }
    }
}