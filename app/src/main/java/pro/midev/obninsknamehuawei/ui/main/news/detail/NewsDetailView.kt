package pro.midev.obninsknamehuawei.ui.main.news.detail

import pro.midev.obninsknamehuawei.common.base.BaseView
import pro.midev.obninsknamehuawei.models.human.NewsHuman

interface NewsDetailView : BaseView {

    fun showNews(news: NewsHuman)
}