package pro.midev.obninsknamehuawei.ui.utils

import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.model.LatLng

private val OBNINSK_POINT = LatLng(55.112005, 36.586531)

fun moveToPoint(mapView: HuaweiMap?, point: LatLng = OBNINSK_POINT, animation: Boolean = false) {
    if (animation) {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(point, 14.0f)
        mapView?.animateCamera(cameraUpdate)
    } else {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(point, 14.0f)
        mapView?.moveCamera(cameraUpdate)
    }
}