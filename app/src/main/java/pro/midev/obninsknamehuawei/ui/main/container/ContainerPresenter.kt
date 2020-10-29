package pro.midev.obninsknamehuawei.ui.main.container

import com.arellomobile.mvp.InjectViewState
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import timber.log.Timber

@InjectViewState
class ContainerPresenter : BasePresenter<ContainerView>() {

    private val bottomVisibilityController: BottomVisibilityController by inject()

    override fun attachView(view: ContainerView?) {
        super.attachView(view)
        bottomVisibilityController.show()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listenBottomVisibility()
    }

    private fun listenBottomVisibility() {
        bottomVisibilityController.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    viewState.changeBottomNavigationVisibility(it)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }
}