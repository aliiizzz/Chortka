package ir.aliiz.hashtag.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.aliiz.common.extension.inflate
import ir.aliiz.domain.model.HashtagDomain
import ir.aliiz.hashtag.R
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
