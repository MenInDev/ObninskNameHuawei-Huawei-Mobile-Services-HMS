package pro.midev.obninsknamehuawei.models.server

import com.google.gson.annotations.SerializedName

data class UnitResponse(
    @SerializedName("Orgs")
    val units: MutableList<UnitItemResponse>?
)

data class UnitItemResponse(
    @SerializedName("ID")
    var id: String?,
    @SerializedName("org")
    var name: String?,
    @SerializedName("place")
    var placeName: String?,
    @SerializedName("adr")
    var address: String?,
    @SerializedName("phon")
    var phone: String?,
    var email: String?,
    @SerializedName("site")
    var url: String?,
    @SerializedName("latitude")
    var lat: String?,
    @SerializedName("longitude")
    var lon: String?,
    var comment: String?
)