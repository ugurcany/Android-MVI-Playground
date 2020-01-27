package dev.ugurcan.mvi_playground.presentation.newslist

import dev.ugurcan.mvi_playground.data.News

sealed class NewsListChange {
    object Loading : NewsListChange()
    data class Data(val newsList: List<News>) : NewsListChange()
    data class Error(val throwable: Throwable?) : NewsListChange()
}