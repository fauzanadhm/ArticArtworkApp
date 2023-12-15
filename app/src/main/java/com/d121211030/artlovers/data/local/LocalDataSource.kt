package com.d121211030.artlovers.data.local

import androidx.lifecycle.LiveData
import com.d121211030.artlovers.data.model.Artwork

interface LocalDataSource {

    val lovedArtwork: LiveData<List<Artwork>?>

    suspend fun listSearchResults(search: String): LiveData<List<Artwork>?>

    suspend fun getArtwork(id: Long): LiveData<Artwork?>
    suspend fun getLovedIds(): List<Long>?

    suspend fun insertListArtworks(artworks: List<Artwork>)
    suspend fun insertArtwork(artwork: Artwork)
    suspend fun deleteArtwork(artwork: Artwork)

}