package pro.midev.obninsknamehuawei.ui.main.gallery.main

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import pro.midev.obninsknamehuawei.common.base.BaseView
import pro.midev.obninsknamehuawei.models.human.GalleryHuman

interface GalleryView : BaseView {

    fun setGallery(gallery: MutableList<GalleryHuman>, maybeMore: Boolean)

    @StateStrategyType(AddToEndStrategy::class)
    fun addGallery(gallery: MutableList<GalleryHuman>, maybeMore: Boolean)
}