package com.data.remote.recipesmodule

import com.data.remote.recipesmodule.response.RecipeListResponse
import com.example.juanhervas.dominio.entities.DataResult

interface RecipesServiceDS {

    suspend fun getRecipeList(): DataResult<RecipeListResponse>

}