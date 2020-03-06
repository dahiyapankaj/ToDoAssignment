package com.todoassignment.data.network

import com.todoassignment.data.models.TodoResponse
import retrofit2.http.GET

interface ApiService {

    /**
     * Retrieves items from Web Api Server
     */
    @GET(Apis.GET_TODOS)
    suspend fun getTodoItemsAsync(): ArrayList<TodoResponse>
}
