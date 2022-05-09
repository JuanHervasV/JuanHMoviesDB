package com.jh.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jh.database.model.MovieResults

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovieResults(moviesResultsModel: List<MovieResults>)

    @Query("DELETE FROM movie_results_table")
    fun deleteAllMovieResults()

    @Query("SELECT * FROM movie_results_table")
    fun readAllMovieResults(): LiveData<List<MovieResults>>
}
