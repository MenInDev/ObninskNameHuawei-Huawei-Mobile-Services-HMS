package pro.midev.obninsknamehuawei.server

import pro.midev.obninsknamehuawei.models.server.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("api.php?get=news&limit=20")
    fun getNews(
        @Query("show") category: String,
        @Query("skip") skip: Int
    ): Single<MutableList<NewsResponse>>

    @GET("api.php?get=article&limit=20")
    fun getArticle(
        @Query("skip") skip: Int
    ): Single<MutableList<ArticleResponse>>

    @GET("api.php?get=head")
    fun getCategories(): Single<MutableList<CategoryResponse>>

    @GET("api.php?get=news&show=all&limit=20")
    fun getCategoryNews(
        @Query("tagID") id: Long,
        @Query("skip") skip: Int
    ): Single<MutableList<NewsResponse>>

    @GET("api.php?get=news&show=all&limit=20")
    fun getDateNews(
        @Query("date") date: String,
        @Query("skip") skip: Int
    ): Single<MutableList<NewsResponse>>

    @GET("api.php?get=news&show=all&limit=20")
    fun getSearchNews(
        @Query("q") query: String,
        @Query("skip") skip: Int
    ): Single<MutableList<NewsResponse>>

    @GET("api.php?get=post")
    fun getDetailNews(@Query("ID") id: Long): Single<MutableList<NewsResponse>>

    @GET("api.php?get=affishe")
    fun getUnits(): Single<UnitResponse>

    @GET("api.php?get=affishe")
    fun getAnnounces(@Query("orgId") id: Long): Single<AnnounceResponse>

    @GET("api.php?get=gallery&limit=22")
    fun getGalleryPhotos(@Query("skip") skip: Int): Observable<MutableList<GalleryResponse>>

    @GET("api.php?get=about")
    fun getAboutInfo(): Observable<AboutResponse>
}