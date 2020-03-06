package com.todoassignment.di

import android.content.Context
import com.todoassignment.App
import com.todoassignment.views.activities.MainActivity
import com.todoassignment.views.fragments.ItemListingFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, ContextModule::class])
interface AppComponent {
    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
    fun inject(itemListingFragment: ItemListingFragment)
    fun inject(context: Context)
}