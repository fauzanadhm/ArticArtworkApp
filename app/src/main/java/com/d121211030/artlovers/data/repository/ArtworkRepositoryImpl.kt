package com.d121211030.artlovers.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.d121211030.artlovers.data.local.LocalDataSource
import com.d121211030.artlovers.data.model.Artwork
import com.d121211030.artlovers.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtworkRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : ArtworkRepository {

    override val lovedArtwork: LiveData<List<Artwork>?> =
        localDataSource.lovedArtwork

    override suspend fun updateIsLoved(artwork: Artwork) {
        Log.i("ArtworkRepositoryImpl", "updating isLoved : $artwork")
        // artwork was not loved before but is now (current value is before update)
        if (artwork.isLoved == false) {
            localDataSource.insertArtwork(artwork.copy(isLoved = true))
        } else {
            localDataSource.deleteArtwork(artwork)
        }
    }

    override suspend fun refreshArtworkDetail(id: Long, isLoved: Boolean?): Flow<Artwork?> {
        Log.i("ArtworkRepositoryImpl", "refreshing detail info : $id")
        return remoteDataSource.getArtworkDetail(id.toString()).map { artwork ->
            artwork?.isLoved = isLoved
            artwork
        }
    }

    override suspend fun getHomeArtwork(search: String?): Flow<List<Artwork>> {
        Log.i("ArtworkRepositoryImpl", "getting home list : $search")
        val loved = localDataSource.getLovedIds()

        return if (search.isNullOrEmpty()) {
            remoteDataSource.listArtwork(1, 50).map { list ->
                list.map { artwork ->
                    if (loved?.contains(artwork.id) == true) {
                        artwork.isLoved = true
                    }
                    artwork
                }
            }
        } else {
            remoteDataSource.listSearchArtwork(search).map { list ->
                list.map { artwork ->
                    if (loved?.contains(artwork.id) == true) {
                        artwork.isLoved = true
                    }
                    artwork
                }
            }
        }
    }

}