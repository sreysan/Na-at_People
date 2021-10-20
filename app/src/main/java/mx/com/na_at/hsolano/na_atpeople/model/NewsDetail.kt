package mx.com.na_at.hsolano.na_atpeople.model

import com.google.gson.annotations.SerializedName

data class NewsDetail(
    val id: String,
    @SerializedName("creationDate")
    var publishDate: String,
    @SerializedName("enabled")
    val isEnabled: Boolean,
    @SerializedName("headline")
    val title: String,
    @SerializedName("body")
    val content: String,
    @SerializedName("image")
    val imageUrl: String
)