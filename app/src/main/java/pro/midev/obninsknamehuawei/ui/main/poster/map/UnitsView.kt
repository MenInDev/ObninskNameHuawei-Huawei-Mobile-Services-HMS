package pro.midev.obninsknamehuawei.ui.main.poster.map

import com.arellomobile.mvp.MvpView
import pro.midev.obninsknamehuawei.models.human.UnitHuman

interface UnitsView : MvpView {

    fun showMarkers(units: MutableList<UnitHuman>)

    fun showUnits(units: MutableList<UnitHuman>)
}