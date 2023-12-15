package com.d121211030.artlovers.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.d121211030.artlovers.DB_NAME
import com.d121211030.artlovers.data.model.Artwork

@Database(entities = [Artwork::class], version = 1)
abstract class ArtDatabase : RoomDatabase() {

    abstract fun artworkDao(): ArtworkDao

    companion object {
        fun create(context: Context): ArtDatabase {
            return Room.databaseBuilder(
                context,
                ArtDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}