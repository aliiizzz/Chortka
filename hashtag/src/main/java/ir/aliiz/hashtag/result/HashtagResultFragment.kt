package ir.aliiz.hashtag.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ir.aliiz.chortka.presentation.main.MainFragmentDirections
import ir.aliiz.common.BaseFragment
import ir.aliiz.common.MainInnerNavigation
import ir.aliiz.common.ViewModelBase
import ir.aliiz.common.ViewModelFactory
import ir.aliiz.hashtag.R
import ir.aliiz.hashtag.di.HashtagComponent
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
        HashtagComponent.create(activity!!).inject(this)
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