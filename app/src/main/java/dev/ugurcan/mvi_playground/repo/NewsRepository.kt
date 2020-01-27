package dev.ugurcan.mvi_playground.repo

import dev.ugurcan.mvi_playground.data.News
import io.reactivex.Observable

interface NewsRepository {
    fun loadAll(): Observable<List<News>>
}

class NewsRepositoryImpl : NewsRepository {
    private val news = listOf(
        News(
            title = "Test news 1",
            content = "Test content 1"
        ),
        News(
            title = "Test news 2",
            content = "Test content 2"
        )
    )

    override fun loadAll(): Observable<List<News>> {
        return Observable.just(news)
    }
}