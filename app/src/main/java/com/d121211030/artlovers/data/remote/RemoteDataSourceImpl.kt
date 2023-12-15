package com.d121211030.artlovers.data.remote

import android.util.Log
import com.d121211030.artlovers.data.model.Artwork
import com.d121211030.artlovers.data.model.toArtworkModel
import com.d121211030.artlovers.network.ArtService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val artService: ArtService,
    private val ioDispatcher: CoroutineDispatcher,
) : RemoteDataSource {
    override suspend fun listArtwork(page: Int, limit: Int): Flow<List<Artwork>> = flow {
        var count = 0
        while (count < FLOW_MAX) {
            try {
                artService.listArtworks(page, limit)
                    .body()?.artworkList?.map { it.toArtworkModel() }?.let {
                        emit(it)
                    }
            } catch (exception: Exception) {
                // TODO handle exceptions
                Log.w("RemoteDataSourceImpl", "Exception at fetchArtwork(): ${exception.message}")
                emit(emptyList())
            }
            count++
        }
    }.flowOn(ioDispatcher)

    override suspend fun listSearchArtwork(query: String): Flow<List<Artwork>> = flow {
        var count = 0
        while (count < FLOW_MAX) {
            try {
                artService.getArtworksSearchResults(query)
                    .body()?.artworkList?.map { it.toArtworkModel() }?.let {
                        emit(it)
                    }
            } catch (exception: Exception) {
                // TODO handle exceptions
                Log.w(
                    "RemoteDataSourceImpl",
                    "Exception at searchArtwork(${query}): ${exception.message}"
                )
                emit(emptyList())
            }
            count++
        }
    }.flowOn(ioDispatcher)

    override suspend fun getArtworkDetail(id: String): Flow<Artwork?> = flow {
        var count = 0
        while (count < FLOW_MAX) {
            try {
                artService.getArtworkDetail(id).body()?.data?.toArtworkModel().let { emit(it) }
            } catch (exception: Exception) {
                // TODO handle exceptions
                Log.w(
                    "RemoteDataSourceImpl",
                    "Exception at getArtworkDetail(${id}): ${exception.message}"
                )
                emit(null)
            }
            count++
        }
    }.flowOn(ioDispatcher)

    companion object {
        const val FLOW_MAX = 1
    }

}