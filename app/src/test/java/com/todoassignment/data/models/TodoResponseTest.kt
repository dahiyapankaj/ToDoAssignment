package com.todoassignment.data.models

import org.junit.Assert
import org.junit.Test

class TodoResponseTest {

    @Test
    fun testToDoResponse() {
        val item = TodoResponse(1, 12, "title", false)
        Assert.assertTrue(item.userId == 1)
        Assert.assertTrue(item.id == 12)
        Assert.assertTrue(item.title == "title")
        Assert.assertTrue(!item.completed)
    }
}