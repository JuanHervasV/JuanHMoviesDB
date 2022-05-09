package com.jh.juanhmoviesdb.feature.searchMedia.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jh.data.feature.popularMovies.remote.MoviesApiService
import com.jh.data.feature.popularMovies.remote.response.Results
import com.jh.juanhmoviesdb.common.constant.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMediaViewModel @Inject constructor(
    private val api: MoviesApiService
) : ViewModel() {
    private val _searchMovies = MutableLiveData<List<Results>>()
    val searchMovies: LiveData<List<Results>> = _searchMovies

    fun searchMovies(word: String) {
        viewModelScope.launch {
            val response = api.searchMovies(API_KEY, word)
            if (response.isSuccessful) {
                _searchMovies.value = response.body()?.results
            }
        }
    }
}