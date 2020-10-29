package pro.midev.obninsknamehuawei

import pro.midev.obninsknamehuawei.models.human.CategoryHuman
import pro.midev.obninsknamehuawei.models.human.GalleryHuman
import pro.midev.obninsknamehuawei.models.human.UnitHuman
import pro.midev.obninsknamehuawei.ui.global.FlowGlobalFragment
import pro.midev.obninsknamehuawei.ui.main.FlowContainerFragment
import pro.midev.obninsknamehuawei.ui.main.container.ContainerFragment
import pro.midev.obninsknamehuawei.ui.main.gallery.FlowGalleryFragment
import pro.midev.obninsknamehuawei.ui.main.gallery.detail.GalleryDetailFragment
import pro.midev.obninsknamehuawei.ui.main.gallery.main.GalleryFragment
import pro.midev.obninsknamehuawei.ui.main.more.FlowMoreFragment
import pro.midev.obninsknamehuawei.ui.main.more.main.InfoFragment
import pro.midev.obninsknamehuawei.ui.main.news.FlowNewsFragment
import pro.midev.obninsknamehuawei.ui.main.news.categories.list.CategoriesFragment
import pro.midev.obninsknamehuawei.ui.main.news.categories.news.CategoryNewsFragment
import pro.midev.obninsknamehuawei.ui.main.news.date.DateNewsFragment
import pro.midev.obninsknamehuawei.ui.main.news.detail.NewsDetailFragment
import pro.midev.obninsknamehuawei.ui.main.news.main.NewsFragment
import pro.midev.obninsknamehuawei.ui.main.news.search.SearchNewsFragment
import pro.midev.obninsknamehuawei.ui.main.poster.FlowPosterFragment
import pro.midev.obninsknamehuawei.ui.main.poster.detail.AnnouncesFragment
import pro.midev.obninsknamehuawei.ui.main.poster.map.UnitsFragment
import pro.midev.obninsknamehuawei.ui.splash.SplashFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    const val APP_ROUTER = "APP_ROUTER"

    object FlowGlobal : SupportAppScreen() {
        override fun getFragment() = FlowGlobalFragment()
    }

    object Splash : SupportAppScreen() {
        override fun getFragment() = SplashFragment()
    }

    const val CONTAINER_ROUTER = "CONTAINER_ROUTER"

    object FlowContainer : SupportAppScreen() {
        override fun getFragment() = FlowContainerFragment()
    }

    object Container : SupportAppScreen() {
        override fun getFragment() = ContainerFragment()
    }

    const val NEWS_ROUTER = "NEWS_ROUTER"

    object FlowNews : SupportAppScreen() {
        override fun getFragment() = FlowNewsFragment()
    }

    object News : SupportAppScreen() {
        override fun getFragment() = NewsFragment()
    }

    object Categories : SupportAppScreen() {
        override fun getFragment() = CategoriesFragment()
    }

    data class CategoryNews(val category: CategoryHuman) : SupportAppScreen() {
        override fun getFragment() = CategoryNewsFragment.create(category)
    }

    data class DateNews(val date: Long) : SupportAppScreen() {
        override fun getFragment() = DateNewsFragment.create(date)
    }

    object SearchNews : SupportAppScreen() {
        override fun getFragment() = SearchNewsFragment()
    }

    data class NewsDetail(val id: Long) : SupportAppScreen() {
        override fun getFragment() = NewsDetailFragment.create(id)
    }

    const val POSTER_ROUTER = "POSTER_ROUTER"

    object FlowPoster : SupportAppScreen() {
        override fun getFragment() = FlowPosterFragment()
    }

    object Map : SupportAppScreen() {
        override fun getFragment() = UnitsFragment()
    }

    data class UnitDetail(val unit: UnitHuman) : SupportAppScreen() {
        override fun getFragment() = AnnouncesFragment.create(unit)
    }

    const val GALLERY_ROUTER = "GALLERY_ROUTER"

    object FlowGallery : SupportAppScreen() {
        override fun getFragment() = FlowGalleryFragment()
    }

    object Gallery : SupportAppScreen() {
        override fun getFragment() = GalleryFragment()
    }

    data class GalleryDetail(val gallery: GalleryHuman) : SupportAppScreen() {
        override fun getFragment() = GalleryDetailFragment.create(gallery)
    }

    const val MORE_ROUTER = "MORE_ROUTER"

    object FlowMore : SupportAppScreen() {
        override fun getFragment() = FlowMoreFragment()
    }

    object Info : SupportAppScreen() {
        override fun getFragment() = InfoFragment()
    }
}