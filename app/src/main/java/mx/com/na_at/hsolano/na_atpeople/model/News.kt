package mx.com.na_at.hsolano.na_atpeople.model

import com.google.gson.annotations.SerializedName

data class News(
    val id: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("creationDate")
    var publishDate: String,
    @SerializedName("headline")
    val title: String,
    val summary: String,
    val body: String,
)