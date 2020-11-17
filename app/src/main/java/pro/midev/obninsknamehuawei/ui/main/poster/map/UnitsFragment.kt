package pro.midev.obninsknamehuawei.ui.main.poster.map

import android.Manifest
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.midev.obninsknamehuawei.BuildConfig
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.extensions.addSystemBottomPadding
import pro.midev.obninsknamehuawei.models.human.UnitHuman
import pro.midev.obninsknamehuawei.ui.utils.moveToPoint
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.fragment_map.*

class UnitsFragment : BaseFragment(R.layout.fragment_map), UnitsView,
    HuaweiMap.OnMarkerClickListener {

    @InjectPresenter
    lateinit var presenter: UnitsPresenter

    private var map: HuaweiMap? = null

    private val adapter by lazy { UnitsAdapter(presenter::onPosterClick) }

    private val markers: MutableMap<Marker?, UnitHuman> = mutableMapOf()

    private val rxPermissions by lazy { RxPermissions(this) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        MapsInitializer.setApiKey(BuildConfig.MAP_API_KEY)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            moveToPoint(map)
            map?.uiSettings?.isZoomControlsEnabled = false
            presenter.loadUnits()
            checkLocationPermission()
        }

        vgTools.addSystemBottomPadding()

        with(rvUnits) {
            adapter = this@UnitsFragment.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val lm = layoutManager as LinearLayoutManager
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        markers.forEach {
                            if (lm.findFirstCompletelyVisibleItemPosition() != -1) {
                                val unit =
                                    this@UnitsFragment.adapter.getCurrentItem(lm.findFirstCompletelyVisibleItemPosition())
                                if (it.value == unit) onMarkerClick(it.key)
                            }
                        }
                    }
                }
            })
        }

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvUnits)
    }

    private fun checkLocationPermission() {
        rxPermissions
            .request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .subscribe {
                if (it) {
                    map?.isMyLocationEnabled = true
                    map?.uiSettings?.isMyLocationButtonEnabled = false
                }
            }
    }

    override fun showMarkers(units: MutableList<UnitHuman>) {
        markers.clear()

        units.forEach {
            markers[map?.addMarker(
                MarkerOptions()
                    .position(LatLng(it.lat, it.lon))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place))
            )] = it
        }

        map?.setOnMarkerClickListener(this)
    }

    override fun showUnits(units: MutableList<UnitHuman>) {
        adapter.addAll(units)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val unit = markers[marker]
        unit?.let {
            moveToPoint(map, LatLng(it.lat, it.lon), true)
            rvUnits.smoothScrollToPosition(adapter.getCurrentItemPosition(it))
        }

        return true
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onBackPressed() {
        presenter.back()
    }
}