package com.d121211030.artlovers.di

import com.d121211030.artlovers.data.local.LocalDataSource
import com.d121211030.artlovers.data.local.LocalDataSourceImpl
import com.d121211030.artlovers.data.remote.RemoteDataSource
import com.d121211030.artlovers.data.remote.RemoteDataSourceImpl
import com.d121211030.artlovers.data.repository.ArtworkRepository
import com.d121211030.artlovers.data.repository.ArtworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    abstract fun bindArtworkRepository(
        artworkRepositoryImpl: ArtworkRepositoryImpl
    ): ArtworkRepository

}