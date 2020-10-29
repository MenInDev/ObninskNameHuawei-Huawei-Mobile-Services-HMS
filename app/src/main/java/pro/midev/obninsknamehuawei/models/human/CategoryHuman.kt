package pro.midev.obninsknamehuawei.models.human

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryHuman(
    val id: String,
    val name: String
) : Parcelable