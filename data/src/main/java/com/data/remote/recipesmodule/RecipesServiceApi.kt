package com.data.remote.recipesmodule

import com.data.common.entities.SingleResponse
import com.data.remote.recipesmodule.response.RecipeListResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecipesServiceApi {

    @GET("/recipeList")
    suspend fun getRecipeServiceList(): Response<SingleResponse<RecipeListResponse>>

}