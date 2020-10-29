package pro.midev.obninsknamehuawei.ui.main.news.detail

import com.arellomobile.mvp.InjectViewState
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.extensions.toNewsHuman
import pro.midev.obninsknamehuawei.server.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class NewsDetailPresenter(
    private val id: Long?
) : BasePresenter<NewsDetailView>() {

    private val service: ApiService by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: NewsDetailView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadDetailNews()
    }

    private fun loadDetailNews() {
        service.getDetailNews(id ?: 0L)
            .map { it.toNewsHuman() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.toggleLoading(true) }
            .doFinally { viewState.toggleLoading(false) }
            .doOnError { viewState.showError(it.message.toString()) }
            .subscribe(
                {
                    viewState.showNews(it.first())
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    fun onAppLinkClick(newsId: String) {
        router?.navigateTo(Screens.NewsDetail(newsId.toLong()))
    }

    fun back() {
        router?.exit()
    }
}