package com.data.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recipes_table")
data class RecipesDataModel(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var origin: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var ingredients: String = "",
    var preparation: String = "",
    var images: String = ""
) : Parcelable