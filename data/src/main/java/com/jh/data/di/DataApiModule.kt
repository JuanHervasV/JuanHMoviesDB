package com.jh.data.di

import android.content.Context
import androidx.room.Room
import com.jh.data.feature.popularMovies.remote.MoviesApiService
import com.jh.database.AppDatabase
import com.jh.database.dao.MoviesDao
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
    fun provideMoviesService(@Named("movies") retrofit: Retrofit): MoviesApiService {
        return retrofit.create(MoviesApiService::class.java)
    }

    @Provides
    fun provideMoviesDao(appDatabase: AppDatabase): MoviesDao =
        appDatabase.getMoviesDao()

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