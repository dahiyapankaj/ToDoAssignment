package com.todoassignment.data.network

import com.todoassignment.data.models.TodoResponse
import org.koin.dsl.module

val itemRepoModule = module {
    factory { ApiRepository(get(), get()) }
}

open class ApiRepository(
    private val networkApi: NetworkApi,
    private val responseHandler: ResponseHandler
) {

    suspend fun getItems(): Resource<List<TodoResponse>> {
        return try {
            val response = networkApi.getTodoItems()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}
