package com.data.remote.recipesmodule

import com.data.common.utils.safeApiCall
import com.data.common.utils.single
import com.data.common.utils.toUnit
import com.data.remote.recipesmodule.response.RecipeListResponse
import com.example.juanhervas.dominio.entities.DataResult
import javax.inject.Inject

class RecipesServiceDSImpl @Inject constructor(
    private val api: RecipesServiceApi
) : RecipesServiceDS {

    override suspend fun getRecipeList(): DataResult<RecipeListResponse> {
        return safeApiCall {
            api.getRecipeServiceList()
        }.single()
    }
}