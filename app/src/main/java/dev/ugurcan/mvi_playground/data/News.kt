package dev.ugurcan.mvi_playground.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class News(
    val title: String,
    val description: String,
    @SerializedName("publishedAt") val date: Date,
    @SerializedName("urlToImage") val image: String
)
