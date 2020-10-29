package pro.midev.obninsknamehuawei.models.server

import com.google.gson.annotations.SerializedName

data class GalleryResponse(
    @SerializedName("image") val image: GalleryImage?,
    @SerializedName("description") val desc: String?,
    @SerializedName("ID") val id: String?
)

data class GalleryImage(
    @SerializedName("small") val smallImg: String,
    @SerializedName("original") val original: String
)