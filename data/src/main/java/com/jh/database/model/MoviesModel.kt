package com.jh.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie_results_table")
data class MovieResults(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var overview: String,
    var image: String,
    var posterImage : String,
    var voteAverage: Double
) : Parcelable