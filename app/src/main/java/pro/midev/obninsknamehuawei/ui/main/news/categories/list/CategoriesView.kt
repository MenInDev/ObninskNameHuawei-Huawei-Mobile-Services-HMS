package pro.midev.obninsknamehuawei.ui.main.news.categories.list

import pro.midev.obninsknamehuawei.common.base.BaseView
import pro.midev.obninsknamehuawei.models.human.CategoryHuman

interface CategoriesView : BaseView {

    fun showCategories(categories: MutableList<CategoryHuman>)
}