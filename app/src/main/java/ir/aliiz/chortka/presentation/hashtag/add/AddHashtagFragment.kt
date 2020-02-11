package ir.aliiz.chortka.presentation.hashtag.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ir.aliiz.chortka.R
import ir.aliiz.chortka.presentation.App
import ir.aliiz.chortka.presentation.ViewModelBase
import ir.aliiz.chortka.presentation.ViewModelFactory
import ir.aliiz.chortka.presentation.hashtag.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_hashtag.*
import javax.inject.Inject

class AddHashtagFragment : BaseFragment() {

    private lateinit var hashstagAdapter: CreateHashtagAdapter
    private lateinit var adapter: CustomHashtagAdapter
    @Inject lateinit var factory: ViewModelFactory
    private val viewmodel: AddHashtagViewModel by viewModels { factory }

    override fun getViewModel(): ViewModelBase = viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context!!.applicationContext as App).component.apply {
            inject(this@AddHashtagFragment)
        }
        adapter = CustomHashtagAdapter {
            viewmodel.addHashtag(it)
        }
        hashstagAdapter = CreateHashtagAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_hashtag, container, false)

    override fun onStart() {
        super.onStart()
        viewmodel.init()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_add_hashtag.adapter = adapter
        recycler_view_add_hashtag.layoutManager = LinearLayoutManager(context)
        recycler_view_custom_hashtag.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_custom_hashtag.adapter = hashstagAdapter
        viewmodel.result.observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
        viewmodel.hashtag.observe(this, Observer {
            hashstagAdapter.items.apply {
                clear()
                addAll(it)
            }
            hashstagAdapter.notifyDataSetChanged()
        })
        button_custom_hashtag_add.setOnClickListener {
            viewmodel.addClicked(edit_text_add_hashtag_name.text.toString())
        }
    }
}