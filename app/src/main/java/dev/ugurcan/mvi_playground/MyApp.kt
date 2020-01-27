package dev.ugurcan.mvi_playground

import android.app.Application
import com.ww.roxie.Roxie
import dev.ugurcan.mvi_playground.presentation.newslist.NewsListViewModel
import dev.ugurcan.mvi_playground.repo.NewsRepository
import dev.ugurcan.mvi_playground.repo.NewsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MyApp : Application() {
    private val repoModule = module {
        // single instance of NewsRepository
        single<NewsRepository> { NewsRepositoryImpl() }
    }

    private val vmModule = module {
        // NewsListViewModel ViewModel
        viewModel { NewsListViewModel(get()) }
    }

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

        // start Koin!
        startKoin {
            androidLogger()
            // Android context
            androidContext(this@MyApp)
            // modules
            modules(listOf(vmModule, repoModule))
        }
    }
}