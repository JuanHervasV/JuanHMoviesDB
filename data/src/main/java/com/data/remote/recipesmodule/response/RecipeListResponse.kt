package com.data.remote.recipesmodule.response

import com.google.gson.annotations.SerializedName

data class RecipeListResponse(
    @SerializedName("responseDescription")
    var responseDescription: String,
    @SerializedName("recipeList")
    var recipeList : List<Recipes>
)

data class Recipes(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("origin")
    var origin: String,
    @SerializedName("latitude")
    var latitude: String,
    @SerializedName("longitude")
    var longitude: String,
    @SerializedName("ingredients")
    var ingredients: String,
    @SerializedName("preparation")
    var preparation: String,
    @SerializedName("images")
    var images: String
)