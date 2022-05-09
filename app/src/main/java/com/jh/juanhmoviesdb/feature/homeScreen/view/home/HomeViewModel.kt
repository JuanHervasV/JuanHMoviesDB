package com.jh.juanhmoviesdb.feature.homeScreen.view.home

import androidx.lifecycle.*
import com.jh.data.feature.popularMovies.remote.MoviesApiService
import com.jh.data.feature.popularMovies.remote.response.Results
import com.jh.database.model.MovieResults
import com.jh.juanhmoviesdb.common.constant.API_KEY
import com.jh.juanhmoviesdb.feature.homeScreen.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: MoviesApiService,
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    private val _popularMovies = MutableLiveData<List<Results>>()
    val popularMovies: LiveData<List<Results>> = _popularMovies

    val moviesDataBaseRoom: LiveData<List<MovieResults>> = moviesRepository.readMoviesDataBase

    fun callPopularMovies() {
        viewModelScope.launch {
            val response = api.getPopularMoviesAsync(API_KEY)
            if (response.isSuccessful) {
                _popularMovies.value = response.body()?.results
            }
        }
    }

    fun addMoviesToDataBase(moviesResultsModel: List<MovieResults>) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.addMoviesDataBase(moviesResultsModel)
        }
    }
}