package com.data.recipesyape.common.di.module

import com.data.recipesyape.common.constant.BASE_URL
import com.data.remote.di.DataApiModule
import com.data.remote.recipesmodule.RecipesServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [DataApiModule::class])
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    @Named("recipes")
    fun provideRetrofitBuilderRecipes(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}