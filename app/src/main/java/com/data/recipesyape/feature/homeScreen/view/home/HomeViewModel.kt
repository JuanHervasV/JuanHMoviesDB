package com.data.recipesyape.feature.homeScreen.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.common.utils.isSuccessful
import com.data.database.model.RecipesDataModel
import com.data.recipesyape.common.utils.toRecipeDataModel
import com.data.recipesyape.feature.homeScreen.domain.repository.RecipeRoomRepository
import com.example.juanhervas.dominio.repositories.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import com.example.juanhervas.dominio.entities.RecipesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipesRepository,
    private val roomRepository: RecipeRoomRepository
) : ViewModel() {

    private val _recipeList = MutableSharedFlow<List<RecipesModel>>()
    var recipeList = _recipeList.asSharedFlow()

    private val _recipeLocalList = MutableSharedFlow<List<RecipesDataModel>>()
    var recipeLocalList = _recipeLocalList.asSharedFlow()

    suspend fun readRecipesDataBaseRoom(): List<RecipesDataModel> = roomRepository.readRecipesDB()

    fun searchRecipes(recipeLetters: String) {
        viewModelScope.launch {
            readRecipesDataBaseRoom()
            if (readRecipesDataBaseRoom().isNotEmpty()) {
                val filteredList = readRecipesDataBaseRoom().filter {
                    it.name.contains(recipeLetters, ignoreCase = true)
                }
                _recipeLocalList.emit(filteredList)
            }
        }
    }

    fun callRecipeList() {
        viewModelScope.launch {
            val result = recipeRepository.callRecipeList()
            if (result.isSuccessful()) {
                _recipeList.emit(result.data)
            }
        }
    }

    fun addRecipesToDataBase(recipeResultsModel: List<RecipesModel>) {
        var transformedRecipesList = toRecipeDataModel(recipeResultsModel)
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.addRecipesDB(transformedRecipesList)
        }
    }
}