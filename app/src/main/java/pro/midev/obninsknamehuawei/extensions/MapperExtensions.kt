package pro.midev.obninsknamehuawei.extensions

import pro.midev.obninsknamehuawei.models.human.*
import pro.midev.obninsknamehuawei.models.server.*


fun MutableList<NewsResponse>.toNewsHuman(): MutableList<NewsHuman> {
    return this.map {
        NewsHuman(
            id = it.id.orEmpty(),
            plus = it.plus ?: 0,
            minus = it.minus ?: 0,
            tag = it.tag.orEmpty(),
            title = it.title.orEmpty(),
            author = it.author.orEmpty(),
            text = it.text.orEmpty(),
            date = it.date?.convertToNewsDate().orEmpty(),
            reviews = it.reviews ?: 0L,
            link = it.link.orEmpty(),
            image = if (it.image?.size != 0) it.image?.first()?.original.orEmpty() else ""
        )
    }.toMutableList()
}

fun MutableList<ArticleResponse>.fromArticleToNewsHuman(): MutableList<NewsHuman> {
    return this.map {
        NewsHuman(
            id = (it.id ?: 0L).toString(),
            title = it.title.orEmpty(),
            text = it.src.orEmpty(),
            date = it.date?.convertToNewsDate().orEmpty(),
            image = it.image.orEmpty()
        )
    }.toMutableList()
}

fun MutableList<CategoryResponse>.toCategoriesHuman(): MutableList<CategoryHuman> {
    return this.map {
        CategoryHuman(
            id = it.id.orEmpty(),
            name = it.tag.orEmpty()
        )
    }.toMutableList()
}

fun UnitResponse.toUnitsHuman(): MutableList<UnitHuman> {
    return this.units?.map {
        UnitHuman(
            id = it.id.orEmpty(),
            name = it.name.orEmpty(),
            placeName = it.placeName.orEmpty(),
            address = it.address.orEmpty(),
            phone = it.phone.orEmpty(),
            email = it.email.orEmpty(),
            url = it.url.orEmpty(),
            lat = it.lat?.replace(",", ".")?.toDoubleOrNull() ?: 0.0,
            lon = it.lon?.replace(",", ".")?.toDoubleOrNull() ?: 0.0,
            comment = it.comment.orEmpty()
        )
    }?.toMutableList() ?: mutableListOf()
}

fun AnnounceResponse.toAnnouncesHuman(): MutableList<AnnounceHuman> {
    return this.events?.map {
        AnnounceHuman(
            place = it.place.orEmpty(),
            date = it.date.orEmpty(),
            time = convertTimeToDuration(it.startTime.orEmpty(), it.endTime.orEmpty()),
            eventName = it.eventName.orEmpty(),
            desc = it.desc.orEmpty(),
            image = it.image.orEmpty(),
            eventType = it.eventType.orEmpty()
        )
    }?.toMutableList() ?: mutableListOf()
}

fun MutableList<GalleryResponse>.toGalleryHuman(): MutableList<GalleryHuman> {
    return this.map {
        GalleryHuman(
            id = it.id.orEmpty(),
            image = it.image?.original.orEmpty(),
            desc = it.desc.orEmpty()
        )
    }.toMutableList()
}