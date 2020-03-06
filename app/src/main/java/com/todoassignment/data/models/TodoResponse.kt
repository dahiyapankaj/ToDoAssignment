package com.todoassignment.data.models
import com.google.gson.annotations.SerializedName

data class TodoResponse (
	@SerializedName("userId") val userId : Int,
	@SerializedName("id") val id : Int,
	@SerializedName("title") val title : String,
	@SerializedName("completed") val completed : Boolean
)