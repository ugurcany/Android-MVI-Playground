package dev.ugurcan.mvi_playground.presentation.newslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dev.ugurcan.mvi_playground.R
import dev.ugurcan.mvi_playground.data.News
import dev.ugurcan.mvi_playground.data.State
import kotlinx.android.synthetic.main.activity_newslist.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsListActivity : AppCompatActivity() {

    // Lazy Inject ViewModel
    private val viewModel: NewsListViewModel by viewModel()

    //create the ItemAdapter holding your Items
    private val itemAdapter = ItemAdapter<News>()

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
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FastAdapter.with(itemAdapter)
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
        itemAdapter.set(newsList)
    }
}
