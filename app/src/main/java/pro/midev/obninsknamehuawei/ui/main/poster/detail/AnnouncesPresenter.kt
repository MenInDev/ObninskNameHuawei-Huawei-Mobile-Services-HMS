package pro.midev.obninsknamehuawei.ui.main.poster.detail

import com.arellomobile.mvp.InjectViewState
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.extensions.toAnnouncesHuman
import pro.midev.obninsknamehuawei.models.human.UnitHuman
import pro.midev.obninsknamehuawei.server.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class AnnouncesPresenter(
    private val unit: UnitHuman?
) : BasePresenter<AnnouncesView>() {

    private val service: ApiService by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: AnnouncesView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadUnit()
        loadAnnounces()
    }

    private fun loadUnit() {
        unit?.let { viewState.showUnitInfo(unit) }
    }

    private fun loadAnnounces() {
        service.getAnnounces(unit?.id?.toLongOrNull() ?: 0L)
            .map { it.toAnnouncesHuman() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.toggleLoading(true) }
            .doFinally { viewState.toggleLoading(false) }
            .doOnError { viewState.showError(it.message.toString()) }
            .subscribe(
                {
                    viewState.showAnnounces(it)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    fun back() {
        router?.exit()
    }
}