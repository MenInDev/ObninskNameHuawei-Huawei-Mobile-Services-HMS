package pro.midev.obninsknamehuawei.models.human

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnitHuman(
    var id: String,
    var name: String,
    var placeName: String,
    var address: String,
    var phone: String,
    var email: String,
    var url: String,
    var lat: Double,
    var lon: Double,
    var comment: String
) : Parcelable