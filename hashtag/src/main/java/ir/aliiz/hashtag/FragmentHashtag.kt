package ir.aliiz.hashtag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import ir.aliiz.common.BaseFragment
import ir.aliiz.common.ViewModelBase
import ir.aliiz.common.ViewModelFactory
import ir.aliiz.hashtag.di.HashtagComponent
import kotlinx.android.synthetic.main.fragment_hashtag.*
import javax.inject.Inject

class FragmentHashtag : BaseFragment() {

    @Inject lateinit var factory: ViewModelFactory
    private val viewModelHashtag by viewModels<ViewModelHashtag> { factory }

    override fun getViewModel(): ViewModelBase = viewModelHashtag

    lateinit var adapter: HashtagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = HashtagAdapter { title, type ->
            viewModelHashtag.updateHashtag(title, type)
        }
        HashtagComponent.create(activity!!).inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_hashtag, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_hashtag.layoutManager = LinearLayoutManager(context)
        recycler_view_hashtag.adapter = adapter
        viewModelHashtag.loadItems.observe(this, Observer {
            it.get()?.also {
                adapter.items = it
                adapter.notifyDataSetChanged()
            }
        })

        button_hashtag_add.setOnClickListener {
            viewModelHashtag.addClicked()
        }

        viewModelHashtag.innerNav.observe(this, Observer {
            it.get()?.also {
                ((parentFragment as NavHostFragment).navController).navigate(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModelHashtag.onResume()
    }
}