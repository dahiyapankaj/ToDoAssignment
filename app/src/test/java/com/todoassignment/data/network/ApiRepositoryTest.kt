package com.todoassignment.data.network

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.todoassignment.data.models.TodoResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import retrofit2.HttpException

class ApiRepositoryTest {
    private val responseHandler = ResponseHandler()
    private lateinit var networkApi: NetworkApi
    private lateinit var apiRepository: ApiRepository
    private var successResource = Resource.success(arrayListOf(TodoResponse(1, 2, "title", false)))
    private var errorResource = Resource.error("Not found", null)


    @Before
    fun setUp() {
        networkApi = mock()
        val mockException: HttpException = mock()
        whenever(mockException.code()).thenReturn(404)
        runBlocking {
            // TODO : uncomment below line to test the failure scenario of getItems() function
//            whenever(networkApi.getTodoItems()).thenThrow(mockException)
            whenever(networkApi.getTodoItems()).thenReturn(successResource.data)
        }
        apiRepository = ApiRepository(
            networkApi,
            responseHandler
        )
    }

    @Test
    fun getItemsSuccessCase() = runBlocking {
        Assert.assertEquals(successResource, apiRepository.getItems())
    }

    // TODO : Remove @Ignore and Uncomment the error case return scenario from setup function
    //  to test this method
    @Ignore
    @Test
    fun getItemsFailureCase() = runBlocking {
        Assert.assertEquals(errorResource, apiRepository.getItems())
    }
}