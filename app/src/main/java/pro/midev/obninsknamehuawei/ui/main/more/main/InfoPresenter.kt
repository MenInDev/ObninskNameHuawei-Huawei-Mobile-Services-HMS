package pro.midev.obninsknamehuawei.ui.main.more.main

import com.arellomobile.mvp.InjectViewState
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.server.ApiService
import pro.midev.obninsknamehuawei.ui.utils.openAboutInfo
import com.huawei.hms.analytics.HiAnalyticsInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class InfoPresenter : BasePresenter<InfoView>() {

    private val service: ApiService by inject()
    private val analytics: HiAnalyticsInstance by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: InfoView?) {
        super.attachView(view)
        bottomVisibilityController.show()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadInfo()
        analytics.openAboutInfo()
    }

    private fun loadInfo() {
        service.getAboutInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.toggleLoading(true) }
            .doFinally { viewState.toggleLoading(false) }
            .doOnError { viewState.showError(it.message.toString()) }
            .subscribe(
                {
                    viewState.showInfo(it.text.orEmpty())
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