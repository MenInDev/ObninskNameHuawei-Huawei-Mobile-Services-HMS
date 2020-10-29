package pro.midev.obninsknamehuawei.ui.main.news.main

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import pro.midev.obninsknamehuawei.common.base.BaseView
import pro.midev.obninsknamehuawei.common.enums.NewsType
import pro.midev.obninsknamehuawei.models.human.NewsHuman

interface NewsView : BaseView {

    fun initCategories(currentNewsType: NewsType)

    fun setNews(news: MutableList<NewsHuman>, maybeMore: Boolean)

    @StateStrategyType(AddToEndStrategy::class)
    fun addNews(news: MutableList<NewsHuman>, maybeMore: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeCategory(type: NewsType)

    @StateStrategyType(SkipStrategy::class)
    fun showCalendar()
}