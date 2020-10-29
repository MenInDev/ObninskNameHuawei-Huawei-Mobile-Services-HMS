package pro.midev.obninsknamehuawei.ui.main.gallery.main

import com.arellomobile.mvp.InjectViewState
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.extensions.toGalleryHuman
import pro.midev.obninsknamehuawei.models.human.GalleryHuman
import pro.midev.obninsknamehuawei.server.ApiService
import pro.midev.obninsknamehuawei.ui.utils.openGallery
import pro.midev.obninsknamehuawei.ui.utils.openGalleryImage
import com.huawei.hms.analytics.HiAnalyticsInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class GalleryPresenter : BasePresenter<GalleryView>() {

    private val service: ApiService by inject()
    private val analytics: HiAnalyticsInstance by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: GalleryView?) {
        super.attachView(view)
        bottomVisibilityController.show()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadGallery(0, false)
        analytics.openGallery()
    }

    fun loadGallery(skip: Int, isPageLoading: Boolean) {
        service.getGalleryPhotos(skip)
            .map { it.toGalleryHuman() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { if (!isPageLoading) viewState.toggleLoading(true) }
            .doFinally { if (!isPageLoading) viewState.toggleLoading(false) }
            .doOnError { viewState.showError(it.message.toString()) }
            .subscribe(
                {
                    if (it.size != 0) onLoadingSuccess(isPageLoading, it)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    private fun onLoadingSuccess(isPageLoading: Boolean, gallery: MutableList<GalleryHuman>) {
        val maybeMore = gallery.size + 1 >= 20
        if (isPageLoading) {
            viewState.addGallery(gallery, maybeMore)
        } else {
            viewState.setGallery(gallery, maybeMore)
        }
    }

    fun onImageClick(gallery: GalleryHuman) {
        router?.navigateTo(Screens.GalleryDetail(gallery))

        analytics.openGalleryImage(gallery)
    }

    fun back() {
        router?.exit()
    }
}