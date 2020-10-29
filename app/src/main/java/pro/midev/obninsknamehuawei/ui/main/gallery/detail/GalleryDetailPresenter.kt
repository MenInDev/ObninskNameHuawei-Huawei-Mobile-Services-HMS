package pro.midev.obninsknamehuawei.ui.main.gallery.detail

import com.arellomobile.mvp.InjectViewState
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.models.human.GalleryHuman
import pro.midev.obninsknamehuawei.ui.utils.openNewsFromGallery
import com.huawei.hms.analytics.HiAnalyticsInstance
import org.koin.core.inject
import ru.terrakok.cicerone.Router

@InjectViewState
class GalleryDetailPresenter(
    private val gallery: GalleryHuman?
) : BasePresenter<GalleryDetailView>() {

    private val analytics: HiAnalyticsInstance by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: GalleryDetailView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        gallery?.let { viewState.showImage(it) }
    }

    fun news(id: String) {
        router?.navigateTo(Screens.NewsDetail(id.toLong()))

        analytics.openNewsFromGallery(id)
    }

    fun back() {
        router?.exit()
    }
}