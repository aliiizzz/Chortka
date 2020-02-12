package ir.aliiz.transaction.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ir.aliiz.common.BaseFragment
import ir.aliiz.common.ViewModelFactory
import ir.aliiz.transaction.R
import ir.aliiz.transaction.TransactionsAdapter
import ir.aliiz.transaction.di.TransactionComponent
import kotlinx.android.synthetic.main.fragment_hashtag_result_detail.*
import javax.inject.Inject

class HashtagResultDetailFragment : BaseFragment() {

    private lateinit var adapter: TransactionsAdapter
    @Inject lateinit var factory: ViewModelFactory
    private val viewmodel by viewModels<HashtagResultDetailViewModel> { factory }
    private val args: HashtagResultDetailFragmentArgs by navArgs()

    override fun getViewModel(): ir.aliiz.common.ViewModelBase = viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransactionComponent.create(activity!!).inject(this)
        adapter = TransactionsAdapter {

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_hashtag_result_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.getHashtagTransactions(args.id)
        recycler_view_hashtag_result_detail.adapter = adapter
        viewmodel.result.observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
    }
}