package ir.aliiz.chortka.presentation.hashtag.result.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ir.aliiz.chortka.R
import ir.aliiz.chortka.presentation.App
import ir.aliiz.chortka.presentation.ViewModelBase
import ir.aliiz.chortka.presentation.ViewModelFactory
import ir.aliiz.chortka.presentation.hashtag.BaseFragment
import ir.aliiz.chortka.presentation.transaction.TransactionsAdapter
import kotlinx.android.synthetic.main.fragment_hashtag_result_detail.*
import javax.inject.Inject

class HashtagResultDetailFragment : BaseFragment() {

    private lateinit var adapter: TransactionsAdapter
    @Inject lateinit var factory: ViewModelFactory
    private val viewmodel by viewModels<HashtagResultDetailViewModel> { factory }
    private val args: HashtagResultDetailFragmentArgs by navArgs()

    override fun getViewModel(): ViewModelBase = viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.applicationContext as App).component.apply {
            inject(this@HashtagResultDetailFragment)
        }
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