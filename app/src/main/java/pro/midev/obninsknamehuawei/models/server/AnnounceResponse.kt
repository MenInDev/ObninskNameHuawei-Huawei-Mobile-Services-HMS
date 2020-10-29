package pro.midev.obninsknamehuawei.models.server

import com.google.gson.annotations.SerializedName

data class AnnounceResponse(
    @SerializedName("Events") val events: MutableList<AnnounceItemResponse>?
)

data class AnnounceItemResponse(
    var place: String?,
    @SerializedName("dat")
    var date: String?,
    @SerializedName("tim")
    var startTime: String?,
    @SerializedName("end")
    var endTime: String?,
    @SerializedName("name")
    var eventName: String?,
    var desc: String?,
    @SerializedName("photo")
    var image: String?,
    @SerializedName("typ")
    var eventType: String?
)