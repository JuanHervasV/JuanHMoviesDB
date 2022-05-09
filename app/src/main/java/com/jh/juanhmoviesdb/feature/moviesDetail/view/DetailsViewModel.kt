package com.jh.juanhmoviesdb.feature.moviesDetail.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jh.data.feature.popularMovies.remote.MoviesApiService
import com.jh.data.feature.popularMovies.remote.response.VideoResult
import com.jh.juanhmoviesdb.common.constant.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val api: MoviesApiService
) : ViewModel() {
    private val _getVideos = MutableLiveData<List<VideoResult>>()
    val getVideos: LiveData<List<VideoResult>> = _getVideos

    fun getVideo(id: Int) {
        viewModelScope.launch {
            val response = api.searchVideos(id, API_KEY)
            if (response.isSuccessful) {
                _getVideos.value = response.body()?.results
            }
        }
    }
}