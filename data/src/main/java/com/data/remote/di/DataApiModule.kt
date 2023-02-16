package com.data.remote.di

import android.content.Context
import androidx.room.Room
import com.data.database.AppDatabase
import com.data.database.dao.RecipesDao
import com.data.remote.recipesmodule.RecipesServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataApiModule {
    @Provides
    fun providesRecipeService(@Named("recipes") retrofit: Retrofit): RecipesServiceApi {
        return retrofit.create(RecipesServiceApi::class.java)
    }

    @Provides
    fun provideRecipesDao(appDatabase: AppDatabase): RecipesDao =
        appDatabase.getRecipesDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RssReader"
        ).build()
    }
}