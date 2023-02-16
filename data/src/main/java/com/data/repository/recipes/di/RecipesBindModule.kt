package com.data.repository.recipes.di

import com.data.remote.recipesmodule.RecipesServiceDS
import com.data.remote.recipesmodule.RecipesServiceDSImpl
import com.data.repository.recipes.RecipesRepositoryImpl
import com.example.juanhervas.dominio.repositories.RecipesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RecipesBindModule {

    @Binds
    abstract fun providesRecipes(repository: RecipesRepositoryImpl): RecipesRepository

    @Binds
    abstract fun bindRecipeServiceDS(recipesServiceDSImpl: RecipesServiceDSImpl): RecipesServiceDS

}