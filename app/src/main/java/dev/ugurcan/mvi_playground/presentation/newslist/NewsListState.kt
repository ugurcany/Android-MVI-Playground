package dev.ugurcan.mvi_playground.presentation.newslist

import com.ww.roxie.BaseState
import dev.ugurcan.mvi_playground.data.News
import dev.ugurcan.mvi_playground.data.State

data class NewsListState(
    val newsList: List<News> = emptyList(),
    val state: State = State.IDLE,
    val errorMessage: String = ""
) : BaseState