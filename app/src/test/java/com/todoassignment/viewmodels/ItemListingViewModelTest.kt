package com.todoassignment.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.todoassignment.data.models.TodoResponse
import com.todoassignment.data.network.ApiRepository
import com.todoassignment.data.network.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

class ItemListingViewModelTest {

    private lateinit var itemListingViewModel: ItemListingViewModel
    private lateinit var apiRepository: ApiRepository
    private val observer: Observer<Resource<List<TodoResponse>>> = mock()

    private var successResource = Resource.success(arrayListOf(TodoResponse(1, 2, "title", false)))
    private var errorResource = Resource.error("Timeout", null)

    // synchronous execution of tasks
    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        apiRepository = mock()
        Dispatchers.setMain(mainThreadSurrogate)
        runBlocking {
            whenever(apiRepository.getItems()).thenReturn(successResource)
            // TODO : uncomment below line to test the failure scenario of getItems() function
//            whenever(apiRepository.getItems()).thenReturn(errorResource)
        }
        itemListingViewModel = ItemListingViewModel(apiRepository)

    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun getItemsSuccessTest() = runBlocking {
        itemListingViewModel.networkStateLiveData.observeForever(observer)
        itemListingViewModel.getItems()
        delay(100)
        verify(apiRepository).getItems()
        verify(observer, timeout(100)).onChanged(Resource.loading(null))
        verify(observer, timeout(100)).onChanged(successResource)
    }

    // TODO : Remove @Ignore and Disconnect from internet to test this method
    @Ignore
    @Test
    fun getItemsFailTest() = runBlocking {
        itemListingViewModel.networkStateLiveData.observeForever(observer)
        itemListingViewModel.getItems()
        delay(10)
        verify(apiRepository).getItems()
        verify(observer, timeout(50)).onChanged(Resource.loading(null))
        verify(observer, timeout(50)).onChanged(errorResource)
    }


}
