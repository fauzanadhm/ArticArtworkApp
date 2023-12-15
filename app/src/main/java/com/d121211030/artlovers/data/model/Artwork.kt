package com.d121211030.artlovers.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Artwork(
    @PrimaryKey
    val id: Long,
    val title: String?,
    val artistDisplay: String?,
    val description: String?,
    val imageId: String?,
    var isLoved: Boolean? = false,
)
