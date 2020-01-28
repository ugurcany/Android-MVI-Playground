package dev.ugurcan.mvi_playground.presentation.newslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import dev.ugurcan.mvi_playground.data.News
import dev.ugurcan.mvi_playground.data.State
import dev.ugurcan.mvi_playground.domain.news.NewsRepository
import dev.ugurcan.mvi_playground.RxTestSchedulerRule
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class NewsListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxTestSchedulerRule()

    private lateinit var testSubject: NewsListViewModel

    private val loadingState = NewsListState(state = State.LOADING)

    private val newsRepo = mock<NewsRepository>()
    private val observer = mock<Observer<NewsListState>>()

    @Before
    fun setUp() {
        testSubject = NewsListViewModel(newsRepo)
        testSubject.observableState.observeForever(observer)
    }

    @Test
    fun `Given news list successfully loaded, when action LoadNewsList is received, then state contains news list`() {
        // GIVEN
        val newsList = listOf(News("title", "description", Date(), "image"))
        val successState = NewsListState(newsList = newsList, state = State.DATA)

        whenever(newsRepo.loadAll("keyword", 1, 1))
            .thenReturn(Observable.just(newsList))

        // WHEN
        testSubject.dispatch(NewsListAction.LoadNewsList("keyword"))
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(successState)
        }
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given news list failed to load, when action LoadNewsList is received, then state contains error`() {
        // GIVEN
        val errorState = NewsListState(errorMessage = "Some err!", state = State.ERROR)

        whenever(newsRepo.loadAll("keyword", 1, 1))
            .thenReturn(Observable.error(RuntimeException()))

        // WHEN
        testSubject.dispatch(NewsListAction.LoadNewsList("keyword"))
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(errorState)
        }
        verifyNoMoreInteractions(observer)
    }
}