package com.todoassignment.data.network

import com.nhaarman.mockitokotlin2.mock
import com.todoassignment.data.models.TodoResponse
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

class ResponseHandlerTest {
    lateinit var responseHandler: ResponseHandler
    private var successData = ArrayList<TodoResponse>()
    private lateinit var httpException: HttpException
    private lateinit var timeoutException: SocketTimeoutException

    @Before
    fun setUp() {
        responseHandler = ResponseHandler()
        successData.add(TodoResponse(1, 12, "title1", false))
        successData.add(TodoResponse(2, 13, "title2", true))

        httpException = HttpException(Response.error<ArrayList<TodoResponse>>(401, mock()))
        timeoutException = SocketTimeoutException()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun handleSuccess() {
        val success = Resource(Status.SUCCESS, successData, null)
        assertEquals(null, success.message)
        assertEquals(Status.SUCCESS, success.status)
        assertEquals(Status.SUCCESS, success.status)
        assertEquals(successData.size, 2)
        assertEquals(successData[0].completed, false)
        assertEquals(successData[1].completed, true)
    }

    @Test
    fun handleException() {
        val httpExceptionResult =
            responseHandler.handleException<ArrayList<TodoResponse>>(httpException)
        val timeOutExceptionResult =
            responseHandler.handleException<ArrayList<TodoResponse>>(timeoutException)

        assertEquals("Unauthorised", httpExceptionResult.message)
        assertEquals(Status.ERROR, httpExceptionResult.status)
        assertEquals("Timeout", timeOutExceptionResult.message)
    }
}