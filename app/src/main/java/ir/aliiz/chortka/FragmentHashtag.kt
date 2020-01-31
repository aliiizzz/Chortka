package ir.aliiz.chortka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ir.aliiz.chortka.domain.model.HashtagDomain
import ir.aliiz.chortka.repository.TransactionRepo
import kotlinx.android.synthetic.main.fragment_hashtag.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FragmentHashtag : Fragment() {

    @Inject lateinit var transactionRepo: TransactionRepo
    lateinit var adapter: HashtagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = HashtagAdapter { id, type ->
            updateHashtag(id, type)
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
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            var hashtags = listOf<HashtagDomain>()
            withContext(Dispatchers.IO) {
                hashtags = transactionRepo.getHashtags()
            }
            adapter.items = hashtags
            adapter.notifyDataSetChanged()
        }
    }

    private fun updateHashtag(id: Long, type: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val item = adapter.items.first { it.id == id }.copy(type = type)
            withContext(Dispatchers.IO) {
                transactionRepo.updateHashtag(item)
            }
            val items = adapter.items.toMutableList().apply {
                this[this.indexOfFirst { it.id == id }] = item
            }.toList()
            adapter.items = items
            adapter.notifyDataSetChanged()
        }
    }
}