package com.d121211030.artlovers.data.model

import com.google.gson.annotations.SerializedName

data class DetailArtworkResponse(
    @SerializedName("data")
    val data: ArtworkResponse
)
