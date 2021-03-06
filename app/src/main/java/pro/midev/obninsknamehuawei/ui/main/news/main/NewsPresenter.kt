package pro.midev.obninsknamehuawei.ui.main.news.main

import com.arellomobile.mvp.InjectViewState
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.common.enums.NewsType
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.extensions.convertToServer
import pro.midev.obninsknamehuawei.extensions.fromArticleToNewsHuman
import pro.midev.obninsknamehuawei.extensions.toNewsHuman
import pro.midev.obninsknamehuawei.models.human.NewsHuman
import pro.midev.obninsknamehuawei.server.ApiService
import pro.midev.obninsknamehuawei.ui.utils.*
import com.huawei.hms.analytics.HiAnalyticsInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.*

@InjectViewState
class NewsPresenter : BasePresenter<NewsView>() {

    private val service: ApiService by inject()
    private val analytics: HiAnalyticsInstance by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    private var currentNewsType = NewsType.ALL

    override fun attachView(view: NewsView?) {
        super.attachView(view)
        bottomVisibilityController.show()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initCategories(currentNewsType)
        loadNews(0, false)
    }

    fun loadNews(skip: Int, isPageLoading: Boolean) {
        if (currentNewsType == NewsType.ARTICLE) {
            service.getArticle(skip)
                .map { it.fromArticleToNewsHuman() }
        } else {
            service.getNews(currentNewsType.convertToServer(), skip)
                .map { it.toNewsHuman() }
        }
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

    fun changeType(newsType: NewsType) {
        currentNewsType = newsType
        loadNews(0, false)
        viewState.changeCategory(newsType)

        analytics.changeNewsType(newsType)
    }

    fun calendar() {
        viewState.showCalendar()

        analytics.useNewsCalendar()
    }

    fun dateNews(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        router?.navigateTo(Screens.DateNews(calendar.timeInMillis))
    }

    fun search() {
        router?.navigateTo(Screens.SearchNews)

        analytics.useNewsSearch()
    }

    fun categories() {
        router?.navigateTo(Screens.Categories)

        analytics.openNewsCategories()
    }

    fun onItemClick(news: NewsHuman) {
        router?.navigateTo(Screens.NewsDetail(news.id.toLong()))

        analytics.openNews(news)
    }

    fun back() {
        router?.exit()
    }
}