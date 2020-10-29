package pro.midev.obninsknamehuawei.models.human

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GalleryHuman(
    val id: String,
    val image: String,
    val desc: String
) : Parcelable