package pro.midev.obninsknamehuawei.ui.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import org.koin.core.inject
import ru.terrakok.cicerone.Router

@InjectViewState
class SplashPresenter : BasePresenter<MvpView>() {

    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        start()
    }

    private fun start() {
        router?.replaceScreen(Screens.FlowContainer)
    }

    fun back() {
        router?.exit()
    }
}