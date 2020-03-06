package com.todoassignment.views.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.todoassignment.App
import com.todoassignment.R
import com.todoassignment.data.adapters.RvAdapter
import com.todoassignment.data.interfaces.ActivityHandler
import com.todoassignment.data.models.TodoResponse
import com.todoassignment.data.network.NetworkState
import com.todoassignment.viewmodels.ItemListingViewModel
import com.todoassignment.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_listing.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ItemListingFragment : Fragment(), RvAdapter.ListItemClickListener {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var adapter: RvAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var activityHandler: ActivityHandler

    private val viewModel: ItemListingViewModel by lazy {
        App.appComponent.inject(this)
        ViewModelProviders.of(this, factory).get(ItemListingViewModel::class.java)
    }

    companion object {
        fun newInstance(): ItemListingFragment {
            return ItemListingFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityHandler = activity as ActivityHandler
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        setObservers()
        viewModel.getItems()
    }

    private fun initUi() {
        linearLayoutManager = LinearLayoutManager(activity)
        rv_items.layoutManager = linearLayoutManager
        //crating a list of items to store items
        val items = listOf<TodoResponse>()
        //creating our adapter
        adapter = RvAdapter(items, this)
        //now adding the adapter to recyclerView
        rv_items.adapter = adapter

        // setting OnClickListener to retry button
        tv_retry.setOnClickListener {
            viewModel.getItems()
        }
    }

    /**
     * Function to set observers for updating UI
     */
    private fun setObservers() {
        viewModel.networkStateLiveData.observe(this, Observer {
            it?.let {
                when (it.state) {
                    NetworkState.FAILED -> {
                        activityHandler.hideProgressBar()
                        ll_error.visibility = VISIBLE
                        tv_error.text = getString(it.messageId)
                    }
                    NetworkState.LOADING -> {
                        activityHandler.showProgressBar()
                        ll_error.visibility = GONE
                    }
                    NetworkState.DATA_LOADED -> {
                        adapter.updateList(it.response)
                        activityHandler.hideProgressBar()
                        ll_error.visibility = GONE
                    }
                    NetworkState.NO_INTERNET -> {
                        tv_error.text = getString(it.messageId)
                        ll_error.visibility = VISIBLE
                        activityHandler.hideProgressBar()
                    }
                }
            }
        })
    }

    override fun OnItemClicked(item: TodoResponse) {
        Toast.makeText(activity, "we clicked item ${item.title}", Toast.LENGTH_SHORT).show()
    }
}

