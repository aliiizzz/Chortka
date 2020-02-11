package ir.aliiz.chortka.presentation.hashtag.result

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.aliiz.chortka.R
import ir.aliiz.chortka.domain.model.HashtagWithAmountDomain
import ir.aliiz.chortka.presentation.hashtag.add.inflate
import kotlinx.android.synthetic.main.item_hashtag_result.view.*

class HashtagResultAdapter (private val click: (title: String) -> Unit): RecyclerView.Adapter<HashtagResultAdapter.HashtagResultViewHolder>() {

    var items: List<HashtagWithAmountDomain> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HashtagResultViewHolder =
        HashtagResultViewHolder(parent.inflate(R.layout.item_hashtag_result)).apply {
            itemView.text_view_hashtag_result_item_amount.setOnClickListener { click(items[adapterPosition].title) }
        }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: HashtagResultViewHolder, position: Int) {
        val item = items[position]
        with(holder.itemView) {
            text_view_hashtag_result_item_amount.text = item.amount.toString()
            text_view_hashtag_result_item_hashtag.text = item.title
        }
    }

    inner class HashtagResultViewHolder(view: View): RecyclerView.ViewHolder(view)
}

