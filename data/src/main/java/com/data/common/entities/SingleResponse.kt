package com.data.common.entities

import com.google.gson.annotations.SerializedName

data class SingleResponse<T>(
    @SerializedName("codRpta")
    val code: String,
    @SerializedName("desRpta")
    val description: String,
    @SerializedName("detRpta")
    val detail: T
)