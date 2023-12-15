package com.d121211030.artlovers.data.remote

import com.d121211030.artlovers.data.model.Artwork
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun listArtwork(page: Int, limit: Int): Flow<List<Artwork>>

    suspend fun listSearchArtwork(query: String): Flow<List<Artwork>>

    suspend fun getArtworkDetail(id: String): Flow<Artwork?>

}