package pro.midev.obninsknamehuawei.models.server

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val id: String?,
    val plus: Int?,
    val minus: Int?,
    val tag: String?,
    val title: String?,
    val author: String?,
    val text: String?,
    val date: Long?,
    @SerializedName("review_count")
    val reviews: Long?,
    val link: String?,
    val image: MutableList<ImageResponse>?
)