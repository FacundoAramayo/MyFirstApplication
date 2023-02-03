package com.fcode.myfirstapplication.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fcode.myfirstapplication.data.repository.SearchRepositoryImpl
import com.fcode.myfirstapplication.domain.Article
import com.fcode.myfirstapplication.utils.safeLaunch
import kotlinx.coroutines.Dispatchers

class SearchViewModel: ViewModel() {

    fun loadNews() {
        viewModelScope.safeLaunch(Dispatchers.IO, {
            SearchRepositoryImpl.fetchNews("", "", "").body()?.let {
                searchResults.postValue(it.articles)
            }
        }, {
            Log.d("LOG-", "Exception: ${it.message}")
        })
    }

    fun loadNewsWithFilters(query: String, fromDate: String, sortBy: String) {
        viewModelScope.safeLaunch(Dispatchers.IO, {
            SearchRepositoryImpl.fetchNews(query, fromDate, sortBy).body()?.let {
                searchResults.postValue(it.articles)
            }
        }, {
            Log.d("LOG-", "Exception: $it")
            searchResults.postValue(listOf())
        })
    }

    private val searchResults = MutableLiveData<List<Article>>()

    fun getObservableSearchResults(): LiveData<List<Article>> = searchResults
}