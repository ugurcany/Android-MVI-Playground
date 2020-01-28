package dev.ugurcan.mvi_playground.presentation.newslist

import androidx.appcompat.widget.AppCompatImageView
import coil.api.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dev.ugurcan.mvi_playground.R
import dev.ugurcan.mvi_playground.data.News
import java.text.DateFormat

class NewsListAdapter :
    BaseQuickAdapter<News, BaseViewHolder>(R.layout.view_newslist_item), LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: News?) {
        helper.setText(R.id.textViewTitle, "#${helper.adapterPosition + 1} - ${item?.title}")
        helper.setText(R.id.textViewDescription, item?.description)
        helper.setText(R.id.textViewDate, DateFormat.getInstance().format(item?.date))
        helper.getView<AppCompatImageView>(R.id.imageViewThumb).load(item?.image) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
            fallback(R.drawable.ic_placeholder)
            error(R.drawable.ic_placeholder)
        }
    }
}