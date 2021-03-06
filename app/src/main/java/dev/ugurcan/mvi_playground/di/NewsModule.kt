package dev.ugurcan.mvi_playground.di

import com.google.gson.GsonBuilder
import dev.ugurcan.mvi_playground.domain.news.NewsApi
import dev.ugurcan.mvi_playground.domain.news.NewsRepository
import dev.ugurcan.mvi_playground.domain.news.NewsRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val newsModule = module {
    // single instance of NewsApi
    single<NewsApi> {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        retrofit.create(NewsApi::class.java)
    }

    // single instance of NewsRepository
    single<NewsRepository> { NewsRepositoryImpl(get()) }
}