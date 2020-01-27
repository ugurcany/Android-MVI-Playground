package dev.ugurcan.mvi_playground.presentation.newslist

import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import dev.ugurcan.mvi_playground.data.State
import dev.ugurcan.mvi_playground.repo.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import io.reactivex.rxkotlin.plusAssign

class NewsListViewModel(private val newsRepository: NewsRepository) :
    BaseViewModel<NewsListAction, NewsListState>() {

    override val initialState = NewsListState(state = State.IDLE)

    private val reducer: Reducer<NewsListState, NewsListChange> = { state, change ->
        when (change) {
            is NewsListChange.Loading -> state.copy(
                state = State.LOADING,
                newsList = emptyList()
            )
            is NewsListChange.Data -> state.copy(
                state = State.DATA,
                newsList = change.newsList
            )
            is NewsListChange.Error -> state.copy(
                state = State.ERROR,
                newsList = emptyList(),
                errorMessage = change.throwable?.message ?: "Something went wrong!"
            )
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val loadNewsChange = actions.ofType<NewsListAction.LoadNewsList>()
            .switchMap {
                newsRepository.loadAll()
                    .subscribeOn(Schedulers.io())
                    .map<NewsListChange> { NewsListChange.Data(it) }
                    .defaultIfEmpty(NewsListChange.Data(emptyList()))
                    .onErrorReturn { NewsListChange.Error(it) }
                    .startWith(NewsListChange.Loading)
            }

        // to handle multiple Changes, use Observable.merge to merge them into a single stream:
        // val allChanges = Observable.merge(loadNotesChange, ...)

        disposables += loadNewsChange
            .scan(initialState, reducer)
            .filter { it.state != State.IDLE }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue, Timber::e)
    }
}