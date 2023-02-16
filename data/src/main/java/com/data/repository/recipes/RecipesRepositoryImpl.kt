package com.data.repository.recipes

import com.data.common.utils.map
import com.data.remote.recipesmodule.RecipesServiceDS
import com.example.juanhervas.dominio.entities.DataResult
import com.example.juanhervas.dominio.entities.RecipesModel
import com.example.juanhervas.dominio.repositories.RecipesRepository
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val remote: RecipesServiceDS,
) : RecipesRepository {
    override suspend fun callRecipeList(): DataResult<List<RecipesModel>> {
        return remote.getRecipeList().map { response ->
            response.recipeList.map {
                RecipesModel(
                    id = it.id,
                    name = it.name,
                    origin = it.origin,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    ingredients = it.ingredients,
                    preparation = it.preparation,
                    images = it.images
                )
            }
        }
    }
}