package com.example.movies.di

import com.example.movies.data.source.remote.IRemoteDataSource
import com.example.movies.data.source.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(
        remoteDataSource: RemoteDataSource
    ) : IRemoteDataSource

}