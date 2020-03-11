package com.todoassignment.data.network

import com.todoassignment.data.models.TodoResponse
import retrofit2.http.GET

interface NetworkApi {

    /**
     * Retrieves items from Web Api Server
     */
    @GET(Apis.GET_TODO)
    suspend fun getTodoItems(): ArrayList<TodoResponse>
}
