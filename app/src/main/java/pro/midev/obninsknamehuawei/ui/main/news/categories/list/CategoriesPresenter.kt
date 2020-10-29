package pro.midev.obninsknamehuawei.ui.main.news.categories.list

import com.arellomobile.mvp.InjectViewState
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.extensions.toCategoriesHuman
import pro.midev.obninsknamehuawei.models.human.CategoryHuman
import pro.midev.obninsknamehuawei.server.ApiService
import pro.midev.obninsknamehuawei.ui.utils.openCategory
import com.huawei.hms.analytics.HiAnalyticsInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class CategoriesPresenter : BasePresenter<CategoriesView>() {

    private val service: ApiService by inject()
    private val analytics: HiAnalyticsInstance by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: CategoriesView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadCategories()
    }

    private fun loadCategories() {
        service.getCategories()
            .map { it.toCategoriesHuman() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.toggleLoading(true) }
            .doFinally { viewState.toggleLoading(false) }
            .doOnError { viewState.showError(it.message.toString()) }
            .subscribe(
                {
                    viewState.showCategories(it)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    fun onCategoryClick(category: CategoryHuman) {
        router?.navigateTo(Screens.CategoryNews(category))

        analytics.openCategory(category)
    }

    fun back() {
        router?.exit()
    }
}