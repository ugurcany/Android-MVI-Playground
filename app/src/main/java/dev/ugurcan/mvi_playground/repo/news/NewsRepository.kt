package dev.ugurcan.mvi_playground.repo.news

import dev.ugurcan.mvi_playground.data.News
import io.reactivex.Observable

const val NEWS_API_KEY = "62ba1f8563aa40a6874b033bdc1b5bca"

interface NewsRepository {
    fun loadAll(): Observable<List<News>>
}

class NewsRepositoryImpl(private val newsApi: NewsApi) :
    NewsRepository {
    override fun loadAll(): Observable<List<News>> {
        return newsApi
            .getNewsList(
                apiKey = NEWS_API_KEY,
                query = "android",
                language = "en",
                sortBy = "publishedAt"
            )
            .map { it.articles }
    }
}