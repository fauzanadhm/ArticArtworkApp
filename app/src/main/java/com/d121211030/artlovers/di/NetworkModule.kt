package com.d121211030.artlovers.di

import com.d121211030.artlovers.ART_BASE_URL
import com.d121211030.artlovers.network.ArtService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideArtService(): ArtService {
        return Retrofit.Builder()
            .baseUrl(ART_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArtService::class.java)
    }

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}