package pro.midev.obninsknamehuawei.ui.main.news.detail

import com.arellomobile.mvp.InjectViewState
import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslator
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.common.base.BasePresenter
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.extensions.toNewsHuman
import pro.midev.obninsknamehuawei.server.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import pro.midev.obninsknamehuawei.common.enums.LanguageType
import pro.midev.obninsknamehuawei.models.human.NewsHuman
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.*

@InjectViewState
class NewsDetailPresenter(
    private val id: Long?
) : BasePresenter<NewsDetailView>() {

    private val service: ApiService by inject()
    private val mlRemoteTranslator: MLRemoteTranslator by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()
    private val navigationHolder: CiceroneHolder by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    private var currentLanguage: LanguageType = LanguageType.RUS

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

    fun translate(news: NewsHuman) {
        currentLanguage = when (currentLanguage) {
            LanguageType.RUS -> LanguageType.ENG
            LanguageType.ENG -> LanguageType.RUS
        }

        when (currentLanguage) {
            LanguageType.RUS -> {
                loadDetailNews()
            }
            LanguageType.ENG -> {
                Observable.just(news)
                    .map {
                        it.title = mlRemoteTranslator.syncTranslate(it.title)
                        it.text = mlRemoteTranslator.syncTranslate(it.text)
                        it.date = mlRemoteTranslator.syncTranslate(it.date)
                        it.tag = mlRemoteTranslator.syncTranslate(it.tag)
                        it.author = mlRemoteTranslator.syncTranslate(it.author)
                        return@map it
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { viewState.toggleLoading(true) }
                    .doFinally { viewState.toggleLoading(false) }
                    .doOnError { viewState.showError(it.message.toString()) }
                    .subscribe(
                        {
                            viewState.showNews(it)
                        },
                        {
                            Timber.e(it)
                        }
                    ).connect()
            }
        }
    }

    fun onAppLinkClick(newsId: String) {
        router?.navigateTo(Screens.NewsDetail(newsId.toLong()))
    }

    fun back() {
        router?.exit()
    }
}