package ir.aliiz.chortka.presentation.hashtag.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ir.aliiz.chortka.R
import ir.aliiz.chortka.presentation.App
import ir.aliiz.chortka.presentation.ViewModelBase
import ir.aliiz.chortka.presentation.ViewModelFactory
import ir.aliiz.chortka.presentation.hashtag.BaseFragment
import ir.aliiz.chortka.presentation.main.MainFragmentDirections
import ir.aliiz.chortka.presentation.main.MainInnerNavigation
import kotlinx.android.synthetic.main.fragment_hashtag_result.*
import javax.inject.Inject

class HashtagResultFragment: BaseFragment() {

    private lateinit var adapter: HashtagResultAdapter
    @Inject lateinit var factory: ViewModelFactory
    private val viewmodel by viewModels<HashtagResultViewModel> { factory }

    override fun getViewModel(): ViewModelBase = viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = HashtagResultAdapter {
            (targetFragment as MainInnerNavigation).navigate(MainFragmentDirections.actionMainToHashtagResultDetail(it))
        }
        (activity!!.applicationContext as App).component.apply {
            inject(this@HashtagResultFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_hashtag_result, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_hashtag_result.adapter =  adapter
        viewmodel.items.observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
        viewmodel.getResult()
    }
}