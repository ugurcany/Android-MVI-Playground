package dev.ugurcan.mvi_playground.domain.news

import dev.ugurcan.mvi_playground.data.News
import io.reactivex.Observable

interface NewsRepository {
    fun loadAll(keyword: String): Observable<List<News>>
}