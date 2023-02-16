package com.example.juanhervas.domain.recipes.repositories

import com.example.juanhervas.domain.recipes.entities.DataResult

interface RecipesRepository {

    suspend fun callRecipeList(): DataResult<Unit>

}