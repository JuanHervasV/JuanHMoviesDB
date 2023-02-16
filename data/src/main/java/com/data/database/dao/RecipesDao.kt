package com.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.data.database.model.RecipesDataModel

@Dao
interface RecipesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipes(recipeResultsModel: List<RecipesDataModel>)

    @Query("DELETE FROM recipes_table")
    suspend fun deleteAllRecipes()

    @Query("SELECT * FROM recipes_table")
    suspend fun readAllRecipes(): List<RecipesDataModel>
}
