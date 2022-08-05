package com.example.movies.di

import com.example.movies.data.repos.IMoviesRepository
import com.example.movies.data.repos.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun provideMoviesRepository(
        moviesRepository: MoviesRepository
    ): IMoviesRepository

}