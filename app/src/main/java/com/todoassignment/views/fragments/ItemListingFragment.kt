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
import androidx.recyclerview.widget.LinearLayoutManager
import com.todoassignment.R
import com.todoassignment.data.adapters.RvAdapter
import com.todoassignment.data.interfaces.ActivityHandler
import com.todoassignment.data.models.TodoResponse
import com.todoassignment.data.network.Resource
import com.todoassignment.data.network.Status
import com.todoassignment.viewmodels.ItemListingViewModel
import kotlinx.android.synthetic.main.fragment_listing.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val fragmentModule = module {
    factory { ItemListingFragment() }
}

/**
 * A simple [Fragment] subclass.
 */
class ItemListingFragment : Fragment(), RvAdapter.ListItemClickListener {

    private val viewModel: ItemListingViewModel by viewModel()

    private lateinit var adapter: RvAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var activityHandler: ActivityHandler

    private val observer = Observer<Resource<List<TodoResponse>>> {
        when (it.status) {
            Status.SUCCESS -> {
                it.data?.let { it1 -> adapter.updateList(it1) }
                activityHandler.hideProgressBar()
                ll_error.visibility = GONE
            }
            Status.ERROR -> {
                tv_error.text = it.message
                ll_error.visibility = VISIBLE
                activityHandler.hideProgressBar()
            }
            Status.LOADING -> {
                activityHandler.showProgressBar()
                ll_error.visibility = GONE
            }
        }
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
        viewModel.networkStateLiveData.observe(this, observer)
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


    override fun OnItemClicked(item: TodoResponse) {
        Toast.makeText(activity, "we clicked item ${item.title}", Toast.LENGTH_SHORT).show()
    }
}

