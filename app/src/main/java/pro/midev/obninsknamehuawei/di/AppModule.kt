package pro.midev.obninsknamehuawei.di

import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsTools
import com.midev.obninsknamehuawei.BuildConfig
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pro.midev.obninsknamehuawei.common.CiceroneHolder
import pro.midev.obninsknamehuawei.controllers.BottomVisibilityController
import pro.midev.obninsknamehuawei.server.Api
import pro.midev.obninsknamehuawei.server.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
    }

    single {
        get<Retrofit.Builder>()
            .baseUrl(BuildConfig.SERVER_URL)
            .build()
    }

    single {
        get<Retrofit>().create(Api::class.java)
    }

    single {
        ApiService(get())
    }

    single {
        CiceroneHolder()
    }

    single {
        HiAnalyticsTools.enableLog()
        HiAnalytics.getInstance(androidContext())
    }

    single {
        BottomVisibilityController()
    }
}