package dev.ugurcan.mvi_playground.di

import dev.ugurcan.mvi_playground.presentation.newslist.NewsListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    // NewsListViewModel
    viewModel { NewsListViewModel(get()) }
}
