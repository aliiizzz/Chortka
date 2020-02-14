package ir.aliiz.transaction.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ir.aliiz.common.BaseFragment
import ir.aliiz.common.ViewModelBase
import ir.aliiz.common.ViewModelFactory
import ir.aliiz.transaction.R
import ir.aliiz.transaction.di.TransactionComponent
import kotlinx.android.synthetic.main.fragment_transaction_detail.*
import javax.inject.Inject

class TransactionDetailFragment : BaseFragment() {
    private lateinit var adapter: TransactionHashtagAdapter
    @Inject lateinit var factory: ViewModelFactory
    private val viewmodel by viewModels<TransactionDetailViewModel> { factory }
    private val args by navArgs<TransactionDetailFragmentArgs>()
    override fun getViewModel(): ViewModelBase = viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TransactionHashtagAdapter { type, id ->
            when (type) {
                TransactionHashtagAdapter.TransactionHashtagType.REMOVE -> viewmodel.removeHashtag(id)
            }
        }
        TransactionComponent.create(activity!!).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_transaction_detail, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_transaction_detail_hashtags.adapter = adapter
        viewmodel.getDetail(args.id)
        viewmodel.detail.observe(this, Observer {
            adapter.items.apply {
                clear()
                addAll(it.hashtags)
            }
            adapter.notifyDataSetChanged()
        })

        button_transaction_detail_add_hashtag.setOnClickListener {
            viewmodel.addHashtag(args.id, edit_text_transaction_detail_add_hashtag.text.toString())
        }
    }
}