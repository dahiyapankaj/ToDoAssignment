package com.todoassignment.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.todoassignment.data.models.TodoResponse
import com.todoassignment.databinding.ListItemBinding

class RvAdapter(
    private var itemList: List<TodoResponse>,
    private val itemClickListener: ListItemClickListener
) :
    RecyclerView.Adapter<RvAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.bindItems(itemList[position])
        holder.itemView.setOnClickListener(View.OnClickListener {
            itemClickListener.OnItemClicked(itemList[position])
        })
    }


    fun updateList(result: List<TodoResponse>) {
        this.itemList = result
        notifyDataSetChanged()
    }

    //the class is holing the list view
    class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var result: TodoResponse
        fun bindItems(result: TodoResponse) {
            this.result = result
            binding.dataModel = result
        }
    }

    interface ListItemClickListener {
        fun OnItemClicked(item: TodoResponse)
    }
}