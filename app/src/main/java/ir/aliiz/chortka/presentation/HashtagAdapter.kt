package ir.aliiz.chortka.presentation

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.aliiz.chortka.R
import ir.aliiz.chortka.domain.model.HashtagDomain
import kotlinx.android.synthetic.main.hashtag_item.view.*

class HashtagAdapter(val clickListener: (title: String, type: Int) -> Unit): RecyclerView.Adapter<HashtagViewHolder>() {
    var items: List<HashtagDomain> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HashtagViewHolder =
        HashtagViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.hashtag_item,
                parent,
                false
            )
        ).apply {
            itemView.image_button_minus.setOnClickListener {
                val item = items[this.adapterPosition]
                clickListener.invoke(item.title, 2)
            }

            itemView.image_button_plus.setOnClickListener {
                val item = items[this.adapterPosition]
                clickListener.invoke(item.title, 1)
            }
        }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: HashtagViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.text_view_hashtag_title.setText(item.title)
        val color = getTextColor(item.type)
        holder.itemView.text_view_hashtag_title.setTextColor(ContextCompat.getColor(holder.itemView.context, color))
        holder.itemView.image_button_minus.setColorFilter(ContextCompat.getColor(holder.itemView.context, color), PorterDuff.Mode.SRC_IN)
        holder.itemView.image_button_plus.setColorFilter(ContextCompat.getColor(holder.itemView.context, color), PorterDuff.Mode.SRC_IN)
    }

    private fun getTextColor(type: Int): Int = when(type) {
        0 -> android.R.color.black
        1 -> android.R.color.holo_blue_dark
        2 -> android.R.color.holo_red_dark
        else -> android.R.color.black
    }
}
class HashtagViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

}