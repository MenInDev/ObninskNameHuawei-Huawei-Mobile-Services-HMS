package pro.midev.obninsknamehuawei.ui.main.news.date

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import pro.midev.obninsknamehuawei.common.base.BaseView
import pro.midev.obninsknamehuawei.models.human.NewsHuman

interface DateNewsView : BaseView {

    fun setNews(news: MutableList<NewsHuman>, maybeMore: Boolean)

    @StateStrategyType(AddToEndStrategy::class)
    fun addNews(news: MutableList<NewsHuman>, maybeMore: Boolean)
}