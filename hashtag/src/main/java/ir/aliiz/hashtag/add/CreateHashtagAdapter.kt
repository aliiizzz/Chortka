package ir.aliiz.hashtag.add

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.aliiz.hashtag.R
import kotlinx.android.synthetic.main.create_hashtag_item.view.*

class CreateHashtagAdapter : RecyclerView.Adapter<CreateHashtagViewHolder>() {
    val items: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateHashtagViewHolder =
        CreateHashtagViewHolder(parent.inflate(R.layout.create_hashtag_item))

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: CreateHashtagViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.text_view_item_create_hashtag_title.setText("#${item}")
    }
}

class CreateHashtagViewHolder(view: View): RecyclerView.ViewHolder(view)