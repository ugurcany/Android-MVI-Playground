package dev.ugurcan.mvi_playground.presentation.newslist

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ugurcan.mvi_playground.R
import dev.ugurcan.mvi_playground.data.News
import dev.ugurcan.mvi_playground.data.State
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

        viewModel.observableState.observe(this, Observer { state ->
            state?.let { renderState(state) }
        })

        viewModel.dispatch(NewsListAction.LoadNewsList)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> LinearLayoutManager(this)
            else -> GridLayoutManager(this, 2)
        }
        recyclerView.adapter = adapter
    }

    private fun renderState(state: NewsListState) {
        when (state.state) {
            State.LOADING -> renderLoading()
            State.ERROR -> renderError(state.errorMessage)
            else -> renderData(state.newsList)
        }
    }

    private fun renderLoading() {
        loadingIndicator.visibility = View.VISIBLE
    }

    private fun renderError(errorMessage: String) {
        loadingIndicator.visibility = View.GONE
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun renderData(newsList: List<News>) {
        loadingIndicator.visibility = View.GONE
        adapter.setNewData(newsList.toMutableList())
    }
}
