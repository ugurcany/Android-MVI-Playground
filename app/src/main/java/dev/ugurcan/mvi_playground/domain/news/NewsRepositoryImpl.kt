package dev.ugurcan.mvi_playground.domain.news

import dev.ugurcan.mvi_playground.Config
import dev.ugurcan.mvi_playground.data.News
import io.reactivex.Observable

class NewsRepositoryImpl(private val newsApi: NewsApi) : NewsRepository {
    override fun loadAll(keyword: String): Observable<List<News>> {
        return newsApi
            .getNewsList(
                apiKey = Config.NEWS_API_KEY,
                query = keyword,
                language = "en",
                sortBy = "publishedAt"
            )
            .map { it.articles }
    }
}