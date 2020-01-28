package dev.ugurcan.mvi_playground.presentation.newslist

import com.ww.roxie.BaseAction

sealed class NewsListAction : BaseAction {
    data class LoadNewsList(val keyword: String) : NewsListAction()
}