package dev.ugurcan.mvi_playground.presentation.newslist

import com.ww.roxie.BaseAction

sealed class NewsListAction : BaseAction {
    object LoadNewsList : NewsListAction()
}