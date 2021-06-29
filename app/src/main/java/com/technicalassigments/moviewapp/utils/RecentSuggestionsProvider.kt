package com.technicalassigments.moviewapp.utils

import android.content.SearchRecentSuggestionsProvider

class RecentSuggestionsProvider: SearchRecentSuggestionsProvider() {

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.technicalassigments.moviewapp.utils.RecentSuggestionsProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }

}