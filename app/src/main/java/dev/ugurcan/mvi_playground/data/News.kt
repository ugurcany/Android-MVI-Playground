package dev.ugurcan.mvi_playground.data

import android.view.View
import com.google.android.material.textview.MaterialTextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import dev.ugurcan.mvi_playground.R

data class News(val title: String, val content: String) : AbstractItem<News.ViewHolder>() {

    /** defines the type defining this item. must be unique. preferably an id */
    override val type: Int
        get() = R.id.unique_id_news

    /** defines the layout which will be used for this item in the list  */
    override val layoutRes: Int
        get() = R.layout.view_newslist_item

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<News>(view) {
        private var title: MaterialTextView = view.findViewById(R.id.textviewTitle)
        private var content: MaterialTextView = view.findViewById(R.id.textviewContent)

        override fun bindView(item: News, payloads: MutableList<Any>) {
            title.text = item.title
            content.text = item.content
        }

        override fun unbindView(item: News) {
            title.text = null
            content.text = null
        }
    }
}