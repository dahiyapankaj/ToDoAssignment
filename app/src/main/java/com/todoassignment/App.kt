package com.todoassignment

import android.app.Application
import com.todoassignment.di.ApiModule
import com.todoassignment.di.AppComponent
import com.todoassignment.di.AppModule
import com.todoassignment.di.DaggerAppComponent

open class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
        appComponent.inject(this)
    }

    private fun initDagger(app: App): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .apiModule(ApiModule())
            .build()


}