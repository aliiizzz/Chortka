package ir.aliiz.chortka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ir.aliiz.chortka.domain.model.HashtagDomain
import ir.aliiz.chortka.domain.model.TransactionInfoDomain
import ir.aliiz.chortka.repository.TransactionRepo
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FragmentTransactions : Fragment() {

    lateinit var adapter: TransactionsAdapter
    @Inject lateinit var transactionRepo: TransactionRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TransactionsAdapter()
        (activity!!.applicationContext as App).component.apply {
            inject(this@FragmentTransactions)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_transactions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_transactions.layoutManager = LinearLayoutManager(context)
        recycler_view_transactions.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            var hashtags = listOf<TransactionInfoDomain>()
            withContext(Dispatchers.IO) {
                hashtags = transactionRepo.getTransactions()
            }
            adapter.items = hashtags.toMutableList()
            adapter.notifyDataSetChanged()
        }
    }
}
