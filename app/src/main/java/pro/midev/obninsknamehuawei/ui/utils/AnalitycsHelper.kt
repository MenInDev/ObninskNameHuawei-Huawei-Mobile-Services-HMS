package pro.midev.obninsknamehuawei.ui.utils

import android.os.Bundle
import pro.midev.obninsknamehuawei.common.enums.NewsType
import pro.midev.obninsknamehuawei.models.human.CategoryHuman
import pro.midev.obninsknamehuawei.models.human.GalleryHuman
import pro.midev.obninsknamehuawei.models.human.NewsHuman
import pro.midev.obninsknamehuawei.models.human.UnitHuman
import com.huawei.hms.analytics.HiAnalyticsInstance

fun HiAnalyticsInstance.changeNewsType(type: NewsType) {
    val bundle = Bundle()
    bundle.putString("type", type.name)
    onEvent("CHANGE_NEWS_TYPE", bundle)
}

fun HiAnalyticsInstance.useNewsCalendar() {
    onEvent("USE_NEWS_CALENDAR", null)
}

fun HiAnalyticsInstance.useNewsSearch() {
    onEvent("USE_NEWS_SEARCH", null)
}

fun HiAnalyticsInstance.openNewsCategories() {
    onEvent("OPEN_NEWS_CATEGORIES", null)
}

fun HiAnalyticsInstance.openNews(news: NewsHuman) {
    val bundle = Bundle()
    bundle.putString("id", news.id)
    bundle.putString("title", news.title)
    onEvent("OPEN_NEWS_DETAIL", bundle)
}

fun HiAnalyticsInstance.openCategory(category: CategoryHuman) {
    val bundle = Bundle()
    bundle.putString("id", category.id)
    bundle.putString("category", category.name)
    onEvent("OPEN_CATEGORY", bundle)
}

fun HiAnalyticsInstance.openMap() {
    onEvent("OPEN_MAP", null)
}

fun HiAnalyticsInstance.openPoster(unit: UnitHuman) {
    val bundle = Bundle()
    bundle.putString("id", unit.id)
    bundle.putString("name", unit.name)
    bundle.putString("place", unit.placeName)
    onEvent("OPEN_POSTER_DETAIL", bundle)
}

fun HiAnalyticsInstance.openGallery() {
    onEvent("OPEN_GALLERY", null)
}

fun HiAnalyticsInstance.openGalleryImage(gallery: GalleryHuman) {
    val bundle = Bundle()
    bundle.putString("id", gallery.id)
    onEvent("OPEN_GALLERY_IMAGE", bundle)
}

fun HiAnalyticsInstance.openNewsFromGallery(id: String) {
    val bundle = Bundle()
    bundle.putString("id", id)
    onEvent("OPEN_NEWS_FROM_GALLERY", bundle)
}

fun HiAnalyticsInstance.openAboutInfo() {
    onEvent("OPEN_ABOUT_INFO", null)
}