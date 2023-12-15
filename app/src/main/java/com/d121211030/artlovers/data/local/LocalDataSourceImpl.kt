package com.d121211030.artlovers.data.local

import androidx.lifecycle.LiveData
import com.d121211030.artlovers.data.model.Artwork
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSourceImpl @Inject constructor(
    private val artworkDao : ArtworkDao
): LocalDataSource {

    override val lovedArtwork: LiveData<List<Artwork>?> = artworkDao.listArtwork()

    override suspend fun listSearchResults(search: String): LiveData<List<Artwork>?> {
        return artworkDao.listArtworkForSearch(search)
    }

    override suspend fun getArtwork(id: Long): LiveData<Artwork?> {
        return artworkDao.getArtworkById(id)
    }

    override suspend fun getLovedIds(): List<Long>? {
        return artworkDao.listArtworkIds()
    }

    override suspend fun insertArtwork(artwork: Artwork) {
        return artworkDao.addArtwork(artwork)
    }

    override suspend fun insertListArtworks(artworks: List<Artwork>) {
        return artworkDao.insertList(artworks)
    }

    override suspend fun deleteArtwork(artwork: Artwork) {
        return artworkDao.delete(artwork)
    }
}