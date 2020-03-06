package com.todoassignment.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoassignment.App
import com.todoassignment.R
import com.todoassignment.data.network.ApiService
import com.todoassignment.data.network.NetworkState
import com.todoassignment.data.network.StateHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class ItemListingViewModel(private val app: App, private val apiService: ApiService) :
    ViewModel() {

    val networkStateLiveData = MutableLiveData<StateHandler>()

    /**
     * Function to get items from web API
     */
    fun getItems() {
        viewModelScope.launch {
            networkStateLiveData.postValue(
                StateHandler(
                    0,
                    NetworkState.LOADING,
                    ArrayList()
                )
            )
            try {
                val dataList = apiService.getTodoItemsAsync()
                if (dataList.isNotEmpty()) {
                    networkStateLiveData.value!!.messageId = R.string.msg_success
                    networkStateLiveData.value!!.state = NetworkState.DATA_LOADED
                    networkStateLiveData.value!!.response = dataList
                } else {
                    networkStateLiveData.value!!.messageId = R.string.err_no_data
                    networkStateLiveData.value!!.state = NetworkState.FAILED
                }
            } catch (ex: Exception) {
                networkStateLiveData.value!!.messageId = getErrorMessageId(ex)
                networkStateLiveData.value!!.state = NetworkState.FAILED
            } finally {
                networkStateLiveData.postValue(networkStateLiveData.value)
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
}

