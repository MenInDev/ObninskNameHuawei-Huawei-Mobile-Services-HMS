package pro.midev.obninsknamehuawei.ui.main.gallery.detail

import com.arellomobile.mvp.MvpView
import pro.midev.obninsknamehuawei.models.human.GalleryHuman

interface GalleryDetailView : MvpView {
    fun showImage(gallery: GalleryHuman)
}