package dev.ugurcan.mvi_playground.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    // NewsListViewModel
    viewModel { NewsListViewModel(get()) }
}
