package com.todoassignment

import android.app.Application
import com.todoassignment.data.network.itemRepoModule
import com.todoassignment.di.networkModule
import com.todoassignment.viewmodels.viewModelModule
import com.todoassignment.views.fragments.fragmentModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    fragmentModule, viewModelModule, networkModule,
                    itemRepoModule
                )
            )
        }
    }
}