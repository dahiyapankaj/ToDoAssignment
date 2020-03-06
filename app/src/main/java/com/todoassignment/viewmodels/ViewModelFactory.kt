package com.todoassignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.todoassignment.App
import com.todoassignment.R
import com.todoassignment.data.network.ApiService
import javax.inject.Inject

class ViewModelFactory @Inject
constructor(private val apiService: ApiService, private val app: App) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemListingViewModel::class.java)) {
            return ItemListingViewModel(app, apiService) as T
        }
        throw IllegalArgumentException(app.getString(R.string.unknown_class))
    }
}
