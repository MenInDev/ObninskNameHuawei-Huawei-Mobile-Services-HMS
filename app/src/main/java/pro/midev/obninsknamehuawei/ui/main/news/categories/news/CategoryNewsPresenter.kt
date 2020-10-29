package pro.midev.obninsknamehuawei.ui.main.news.categories.news

import com.arellomobile.mvp.InjectViewState
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.extensions.toNewsHuman
import pro.midev.obninsknamehuawei.models.human.NewsHuman
import pro.midev.obninsknamehuawei.server.ApiService
import pro.midev.obninsknamehuawei.ui.utils.openNews
import com.huawei.hms.analytics.HiAnalyticsInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class CategoryNewsPresenter(
    private val id: Long?
) : BasePresenter<CategoryNewsView>() {

    private val service: ApiService by inject()
    private val analytics: HiAnalyticsInstance by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: CategoryNewsView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadNews(0, false)
    }

    fun loadNews(skip: Int, isPageLoading: Boolean) {
        service.getCategoryNews(id ?: 0L, skip)
            .map { it.toNewsHuman() }
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

    private fun onLoadingSuccess(isPageLoading: Boolean, news: MutableList<NewsHuman>) {
        val maybeMore = news.size + 1 >= 20
        if (isPageLoading) {
            viewState.addNews(news, maybeMore)
        } else {
            viewState.setNews(news, maybeMore)
        }
    }

    fun onItemClick(news: NewsHuman) {
        router?.navigateTo(Screens.NewsDetail(news.id.toLong()))

        analytics.openNews(news)
    }

    fun back() {
        router?.exit()
    }
}