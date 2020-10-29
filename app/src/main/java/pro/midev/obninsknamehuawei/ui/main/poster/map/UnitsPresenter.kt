package pro.midev.obninsknamehuawei.ui.main.poster.map

import com.arellomobile.mvp.InjectViewState
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.extensions.toUnitsHuman
import pro.midev.obninsknamehuawei.models.human.UnitHuman
import pro.midev.obninsknamehuawei.server.ApiService
import pro.midev.obninsknamehuawei.ui.utils.openMap
import pro.midev.obninsknamehuawei.ui.utils.openPoster
import com.huawei.hms.analytics.HiAnalyticsInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class UnitsPresenter : BasePresenter<UnitsView>() {

    private val service: ApiService by inject()
    private val analytics: HiAnalyticsInstance by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: UnitsView?) {
        super.attachView(view)
        bottomVisibilityController.show()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        analytics.openMap()
    }

    fun loadUnits() {
        service.getUnits()
            .map { it.toUnitsHuman() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    viewState.showMarkers(it)
                    viewState.showUnits(it)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    fun onPosterClick(unit: UnitHuman) {
        router?.navigateTo(Screens.UnitDetail(unit))

        analytics.openPoster(unit)
    }

    fun back() {
        router?.exit()
    }
}