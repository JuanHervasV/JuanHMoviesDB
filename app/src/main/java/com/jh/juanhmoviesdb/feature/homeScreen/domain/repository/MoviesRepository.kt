package com.jh.juanhmoviesdb.feature.homeScreen.domain.repository

import androidx.lifecycle.LiveData
import com.jh.database.dao.MoviesDao
import com.jh.database.model.MovieResults
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesDao: MoviesDao
) {
    val readMoviesDataBase: LiveData<List<MovieResults>> = moviesDao.readAllMovieResults()

    fun addMoviesDataBase(moviesResultsModel: List<MovieResults>) {
        moviesDao.addMovieResults(moviesResultsModel)
    }
}