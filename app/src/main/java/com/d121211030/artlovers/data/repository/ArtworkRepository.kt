package com.d121211030.artlovers.data.repository

import androidx.lifecycle.LiveData
import com.d121211030.artlovers.data.model.Artwork
import kotlinx.coroutines.flow.Flow

interface ArtworkRepository {

    val lovedArtwork: LiveData<List<Artwork>?>

    suspend fun updateIsLoved(artwork: Artwork)

    suspend fun refreshArtworkDetail(id: Long, isLoved: Boolean?): Flow<Artwork?>

    suspend fun getHomeArtwork(search: String? = null): Flow<List<Artwork>>

}