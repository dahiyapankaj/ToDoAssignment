package com.todoassignment.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoassignment.R
import com.todoassignment.data.models.TodoResponse
import com.todoassignment.data.network.ApiRepository
import com.todoassignment.data.network.Resource
import kotlinx.coroutines.launch
import org.koin.dsl.module
import retrofit2.HttpException
import java.net.UnknownHostException

val viewModelModule = module {
    factory { ItemListingViewModel(get()) }
}

class ItemListingViewModel(private val apiRepository: ApiRepository) :
    ViewModel() {

    val networkStateLiveData = MutableLiveData<Resource<List<TodoResponse>>>()

    /**
     * Function to get items from web API
     */
    fun getItems() {
        networkStateLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = apiRepository.getItems()
            networkStateLiveData.postValue(response)
        }
    }
}

/**
 * Function to return message Id of String resources as per the exception
 */
fun getErrorMessageId(ex: Exception): Int {
    when (ex) {
        is UnknownHostException -> {
            return R.string.check_network_settings
        }
        is HttpException -> {
            return getHttpErrorMessage(ex.code())
        }
    }
    return R.string.err_default
}

/**
 * Function to return Http Error Code message Ids
 */
fun getHttpErrorMessage(code: Int): Int {
    return when (code) {
        400 -> R.string.err_400
        401 -> R.string.err_401
        403 -> R.string.err_403
        404 -> R.string.err_404
        405 -> R.string.err_405
        408 -> R.string.err_408
        500 -> R.string.err_500
        502 -> R.string.err_502
        503 -> R.string.err_503
        else -> R.string.err_default
    }
}


