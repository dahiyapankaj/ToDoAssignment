package com.todoassignment.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.todoassignment.App
import com.todoassignment.R
import com.todoassignment.data.network.ApiService
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class ItemListingViewModelTest {

    private lateinit var itemListingViewModel: ItemListingViewModel
    private var app = mock<App>()
    private var apiService = mock<ApiService>()


    private var exIo = mock<IOException>()
    private var exHttp = mock<HttpException>()
    private var exUnkown = mock<UnknownHostException>()

    // synchronous execution of tasks
    @get:Rule
    val executorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        itemListingViewModel = ItemListingViewModel(app, apiService)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getItemsTest() = runBlockingTest {
        //        itemListingViewModel.getItems()
//        MatcherAssert.assertThat(itemListingViewModel.networkStateLiveData.value?.response?.size, CoreMatchers.not(0))
    }

    @Test
    fun getErrorMessageIdTest() {
        Assert.assertEquals(
            itemListingViewModel.getErrorMessageId(exUnkown),
            R.string.check_network_settings
        )
        Assert.assertEquals(itemListingViewModel.getErrorMessageId(exIo), R.string.err_default)
        Assert.assertEquals(itemListingViewModel.getErrorMessageId(exHttp), R.string.err_default)
    }

    @Test
    fun getHttpErrorMessageTest() {
        Assert.assertEquals(itemListingViewModel.getHttpErrorMessage(402), R.string.err_default)
        Assert.assertEquals(itemListingViewModel.getHttpErrorMessage(400), R.string.err_400)
        Assert.assertEquals(itemListingViewModel.getHttpErrorMessage(401), R.string.err_401)
        Assert.assertEquals(itemListingViewModel.getHttpErrorMessage(403), R.string.err_403)
        Assert.assertEquals(itemListingViewModel.getHttpErrorMessage(404), R.string.err_404)
        Assert.assertEquals(itemListingViewModel.getHttpErrorMessage(405), R.string.err_405)
        Assert.assertEquals(itemListingViewModel.getHttpErrorMessage(408), R.string.err_408)
        Assert.assertEquals(itemListingViewModel.getHttpErrorMessage(500), R.string.err_500)
        Assert.assertEquals(itemListingViewModel.getHttpErrorMessage(502), R.string.err_502)
        Assert.assertEquals(itemListingViewModel.getHttpErrorMessage(503), R.string.err_503)
    }
}