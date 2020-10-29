package pro.midev.obninsknamehuawei.models.server

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("tagID")
    val id: String?,
    val tag: String?
)