package pro.midev.obninsknamehuawei.ui.main.poster.detail

import pro.midev.obninsknamehuawei.common.base.BaseView
import pro.midev.obninsknamehuawei.models.human.AnnounceHuman
import pro.midev.obninsknamehuawei.models.human.UnitHuman

interface AnnouncesView : BaseView {

    fun showUnitInfo(unit: UnitHuman)

    fun showAnnounces(announces: MutableList<AnnounceHuman>)
}