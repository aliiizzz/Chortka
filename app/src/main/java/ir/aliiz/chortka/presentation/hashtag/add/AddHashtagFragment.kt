package ir.aliiz.chortka.presentation.hashtag.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ir.aliiz.chortka.R
import ir.aliiz.chortka.presentation.ViewModelFactory
import ir.aliiz.chortka.presentation.hashtag.FragmentBase
import javax.inject.Inject

class AddHashtagFragment : FragmentBase() {

    @Inject lateinit var factory: ViewModelFactory
    private val viewmodel: AddHashtagViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CustomHashtagAdapter()
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
        viewmodel.result.observe(this, Observer {

        })
    }
}