package com.todoassignment.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.todoassignment.BuildConfig
import com.todoassignment.data.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {
    private val baseUrl = BuildConfig.BASE_URL
    private val timeOutSecs = 60L

    @Singleton
    @Provides
    fun provideRetroFitApi(okHttpClient: OkHttpClient, gSonFactory : GsonConverterFactory) : ApiService {
        val instance = Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(gSonFactory)
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
        return instance.create(ApiService::class.java)
    }
    @Singleton
    @Provides
    fun provideHttpFactory(): OkHttpClient {
        return OkHttpClient().newBuilder().
            readTimeout(timeOutSecs, TimeUnit.SECONDS)
            .connectTimeout(timeOutSecs, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideJsonFactory() : GsonConverterFactory {
        return  GsonConverterFactory.create()
    }


}