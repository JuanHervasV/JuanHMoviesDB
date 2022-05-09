package com.jh.data.feature.popularMovies.remote.response

import com.google.gson.annotations.SerializedName

data class VideosMoviesResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<VideoResult>

)

data class VideoResult(
    @SerializedName("id")
    var id: String,
    @SerializedName("key")
    var key: String,
    @SerializedName("site")
    var site: String
)
