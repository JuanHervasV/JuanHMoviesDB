package com.jh.data.feature.popularMovies.remote.response

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<Results>
)

data class Results(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("poster_path")
    var image: String,
    @SerializedName("backdrop_path")
    var posterimage: String,
    @SerializedName("vote_average")
    var voteAverage: Double
)