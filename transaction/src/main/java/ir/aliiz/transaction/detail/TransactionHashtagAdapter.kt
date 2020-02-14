package ir.aliiz.transaction.detail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.aliiz.common.extension.inflate
import ir.aliiz.transaction.R
import kotlinx.android.synthetic.main.item_transaction_hashtag.view.*

class TransactionHashtagAdapter(private val listener: (type: TransactionHashtagType, id: String) -> Unit) : RecyclerView.Adapter<TransactionHashtagAdapter.ViewHolder>() {
    val items: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(
        R.layout.item_transaction_hashtag)).apply {
        this.itemView.text_view_item_transaction_hashtag_title.setOnLongClickListener {
            listener(TransactionHashtagType.REMOVE, items[adapterPosition])
            true
        }
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.text_view_item_transaction_hashtag_title.text = item
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    enum class TransactionHashtagType {
        REMOVE, CLICK
    }
}