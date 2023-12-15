package com.d121211030.artlovers.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.d121211030.artlovers.data.model.Artwork

@Dao
interface ArtworkDao {

    @Query("SELECT * FROM artwork ORDER BY title ASC")
    fun listArtwork(): LiveData<List<Artwork>?>

    @Query("SELECT id FROM artwork")
    suspend fun listArtworkIds(): List<Long>?

    @Query("SELECT * FROM artwork WHERE title LIKE :search")
    fun listArtworkForSearch(search: String): LiveData<List<Artwork>?>

    @Query("SELECT * FROM artwork WHERE id=:id LIMIT 1")
    fun getArtworkById(id: Long): LiveData<Artwork?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArtwork(artwork: Artwork)

    @Transaction
    suspend fun insertList(list: List<Artwork>) {
        for (artwork in list) {
            addArtwork(artwork)
        }
    }

    @Delete
    suspend fun delete(artwork: Artwork)
}