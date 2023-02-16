package com.data.recipesyape.feature.homeScreen.domain.repository

import com.data.database.dao.RecipesDao
import com.data.database.model.RecipesDataModel
import javax.inject.Inject

class RecipeRoomRepository @Inject constructor(
    private val recipesDao: RecipesDao
) {
    suspend fun readRecipesDB(): List<RecipesDataModel> = recipesDao.readAllRecipes()

    suspend fun addRecipesDB(recipesDataModel: List<RecipesDataModel>) {
        recipesDao.addRecipes(recipesDataModel)
    }
}