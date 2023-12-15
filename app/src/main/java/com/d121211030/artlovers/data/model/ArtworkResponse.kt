package com.d121211030.artlovers.data.model

import com.google.gson.annotations.SerializedName

data class ArtworkResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String?,
    @SerializedName("artist_display")
    val artistDisplay: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image_id")
    val imageId: String?,
)

fun ArtworkResponse.toArtworkModel() : Artwork {
    return Artwork(
        id = id,
        title = title,
        artistDisplay = artistDisplay,
        description = description,
        imageId = imageId
        )
}
