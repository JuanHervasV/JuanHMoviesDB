package com.jh.data.feature.popularMovies.remote

import com.jh.data.feature.popularMovies.remote.response.PopularMoviesResponse
import com.jh.data.feature.popularMovies.remote.response.VideosMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("/3/movie/popular")
    suspend fun getPopularMoviesAsync(
        @Query("api_key") api_key: String,
    ): Response<PopularMoviesResponse>

    @GET("/3/search/movie")
    suspend fun searchMovies(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ): Response<PopularMoviesResponse>

    @GET("/3/movie/{id}/videos")
    suspend fun searchVideos(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
    ): Response<VideosMoviesResponse>
}