package dev.ugurcan.mvi_playground

import android.app.Application
import com.ww.roxie.Roxie
import dev.ugurcan.mvi_playground.di.newsModule
import dev.ugurcan.mvi_playground.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Roxie
        Roxie.enableLogging(object : Roxie.Logger {
            override fun log(msg: String) {
                Timber.tag("Roxie").d(msg)
            }
        })

        // Koin
        startKoin {
            androidLogger()
            // Android context
            androidContext(this@MyApp)
            // modules
            modules(listOf(vmModule, newsModule))
        }
    }
}