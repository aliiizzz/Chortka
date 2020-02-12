package ir.aliiz.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ir.aliiz.common.ViewModelFactory
import ir.aliiz.transaction.di.TransactionComponent
import kotlinx.android.synthetic.main.fragment_transactions.*
import javax.inject.Inject

class TransactionsFragment : Fragment() {

    lateinit var adapter: TransactionsAdapter
    @Inject lateinit var factory: ViewModelFactory
    private val viewmodel: TransactionViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TransactionsAdapter {
        }
        TransactionComponent.create(activity!!).inject(this)
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
        viewmodel.updateItems.observe(this, Observer {
            adapter.items = it.toMutableList()
            adapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getTransactions()
    }
}
