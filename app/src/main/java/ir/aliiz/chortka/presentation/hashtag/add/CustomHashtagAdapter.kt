package ir.aliiz.chortka.presentation.hashtag.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.aliiz.chortka.R
import ir.aliiz.chortka.domain.model.HashtagDomain
import kotlinx.android.synthetic.main.hashtag_item.view.*
import kotlinx.android.synthetic.main.item_custom_hashtag.view.*

class CustomHashtagAdapter(val clickListener: (String) -> Unit) : RecyclerView.Adapter<CustomHashtagViewHolder>() {
    var items: List<HashtagDomain> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHashtagViewHolder =
        CustomHashtagViewHolder(parent.inflate(R.layout.item_custom_hashtag)).apply {
            itemView.text_view_item_custom_hashtag_title.setOnClickListener { clickListener.invoke(items[adapterPosition].title) }
        }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: CustomHashtagViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.text_view_item_custom_hashtag_title.setText("#${item.title}")
    }
}

class CustomHashtagViewHolder(view: View): RecyclerView.ViewHolder(view)

fun ViewGroup.inflate(resId: Int): View = LayoutInflater.from(this.context).inflate(resId, this, false)