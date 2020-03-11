package com.todoassignment.di

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // TODO : Uncomment below two lines for adding query params
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
//        val url = req.url.newBuilder().addQueryParameter("APPID", "your_key").build()
//        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}