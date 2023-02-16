package com.example.juanhervas.domain.recipes.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeList(
    var id: String,
    var name: String,
    var origin: String,
    var latitude: String,
    var longitude: Strig,
    var ingredients: String,
    var preparation: String,
    var images: String
) : Parcelable