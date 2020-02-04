package ir.aliiz.chortka.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.aliiz.chortka.R
import ir.aliiz.chortka.domain.model.TransactionInfoDomain
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionsAdapter : RecyclerView.Adapter<TransactionViewHolder>() {
    var items: MutableList<TransactionInfoDomain> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder =
        TransactionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.transaction_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.text_view_amount.setText("${item.amount} - ${item.hashtags.joinToString(" ")}")
    }
}

class TransactionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

}