package com.example.juanhervas.dominio.repositories

import com.example.juanhervas.dominio.entities.DataResult
import com.example.juanhervas.dominio.entities.RecipesModel

interface RecipesRepository {

    suspend fun callRecipeList(): DataResult<List<RecipesModel>>

}