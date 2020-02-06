package ir.aliiz.chortka.presentation.hashtag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ir.aliiz.chortka.R
import ir.aliiz.chortka.domain.model.HashtagDomain
import ir.aliiz.chortka.presentation.App
import ir.aliiz.chortka.presentation.HashtagAdapter
import ir.aliiz.chortka.presentation.ViewModelFactory
import ir.aliiz.chortka.presentation.main.MainInnerNavigation
import ir.aliiz.chortka.repository.TransactionRepo
import kotlinx.android.synthetic.main.fragment_hashtag.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FragmentHashtag : Fragment() {

    @Inject lateinit var factory: ViewModelFactory
    private val viewModelHashtag by viewModels<ViewModelHashtag> { factory }

    lateinit var adapter: HashtagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = HashtagAdapter { title, type ->
            viewModelHashtag.updateHashtag(title, type)
        }
        (activity!!.applicationContext as App).component.apply {
            inject(this@FragmentHashtag)
        }
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