package pro.midev.obninsknamehuawei.server

class ApiService(private val api: Api) {

    fun getNews(category: String, skip: Int) = api.getNews(category, skip)

    fun getArticle(skip: Int) = api.getArticle(skip)

    fun getCategories() = api.getCategories()

    fun getCategoryNews(id: Long, skip: Int) = api.getCategoryNews(id, skip)

    fun getDateNews(date: String, skip: Int) = api.getDateNews(date, skip)

    fun getSearchNews(query: String, skip: Int) = api.getSearchNews(query, skip)

    fun getDetailNews(id: Long) = api.getDetailNews(id)

    fun getUnits() = api.getUnits()

    fun getAnnounces(id: Long) = api.getAnnounces(id)

    fun getGalleryPhotos(skip: Int) = api.getGalleryPhotos(skip)

    fun getAboutInfo() = api.getAboutInfo()
}