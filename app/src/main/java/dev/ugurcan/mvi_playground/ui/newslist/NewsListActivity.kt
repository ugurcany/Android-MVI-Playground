package dev.ugurcan.mvi_playground.ui.newslist

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ugurcan.mvi_playground.R
import dev.ugurcan.mvi_playground.data.News
import dev.ugurcan.mvi_playground.data.State
import dev.ugurcan.mvi_playground.presentation.newslist.NewsListAction
import dev.ugurcan.mvi_playground.presentation.newslist.NewsListState
import dev.ugurcan.mvi_playground.presentation.newslist.NewsListViewModel
import kotlinx.android.synthetic.main.activity_newslist.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsListActivity : AppCompatActivity() {

    // Lazy Inject ViewModel
    private val viewModel: NewsListViewModel by viewModel()

    private val adapter = NewsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newslist)
        setupRecyclerView()
        setupSwipeRefreshLayout()

        viewModel.observableState.observe(this, Observer { state ->
            state?.let { renderState(state) }
        })

        loadData(false)
    }

    private fun loadData(requestNextPage: Boolean) {
        if (requestNextPage) viewModel.page++
        else viewModel.page = 1
        viewModel.dispatch(NewsListAction.LoadNewsList("android"))
    }

    private fun setupRecyclerView() {
        adapter.loadMoreModule?.let {
            it.setOnLoadMoreListener {
                loadData(true)
            }
            it.isAutoLoadMore = true
        }

        recyclerView.layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> LinearLayoutManager(this)
            else -> GridLayoutManager(this, 2)
        }
        recyclerView.adapter = adapter
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            loadData(false)
        }
    }

    private fun renderState(state: NewsListState) {
        when (state.state) {
            State.LOADING -> renderLoading()
            State.ERROR -> renderError(state.errorMessage)
            else -> renderData(state.newsList)
        }
    }

    private fun renderLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    private fun renderError(errorMessage: String) {
        adapter.loadMoreModule?.loadMoreComplete()
        swipeRefreshLayout.isRefreshing = false
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun renderData(newsList: List<News>) {
        swipeRefreshLayout.isRefreshing = false

        if (viewModel.page == 1)
            adapter.setNewData(newsList.toMutableList())
        else {
            adapter.addData(newsList.toMutableList())
            if (newsList.size < viewModel.pageSize)
                adapter.loadMoreModule?.loadMoreEnd()
            else
                adapter.loadMoreModule?.loadMoreComplete()
        }
    }
}
