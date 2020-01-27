package dev.ugurcan.mvi_playground.repo.news

import dev.ugurcan.mvi_playground.data.NewsListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    fun getNewsList(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String
    ): Observable<NewsListResponse>

}