package pro.midev.obninsknamehuawei

import android.app.Application
import pro.midev.obninsknamehuawei.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            koin.loadModules(mutableListOf(appModule))
            koin.createRootScope()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}