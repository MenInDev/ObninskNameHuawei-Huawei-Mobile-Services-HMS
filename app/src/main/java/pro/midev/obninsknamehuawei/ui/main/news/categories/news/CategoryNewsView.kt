package pro.midev.obninsknamehuawei.ui.main.news.categories.news

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import pro.midev.obninsknamehuawei.common.base.BaseView
import pro.midev.obninsknamehuawei.models.human.NewsHuman

interface CategoryNewsView : BaseView {

    fun setNews(news: MutableList<NewsHuman>, maybeMore: Boolean)

    @StateStrategyType(AddToEndStrategy::class)
    fun addNews(news: MutableList<NewsHuman>, maybeMore: Boolean)
}