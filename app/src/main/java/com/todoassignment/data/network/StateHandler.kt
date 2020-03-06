package com.todoassignment.data.network

import com.todoassignment.data.models.TodoResponse

class StateHandler(
    var messageId: Int,
    var state: NetworkState,
    var response: ArrayList<TodoResponse>
)