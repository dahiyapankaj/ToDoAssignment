package com.todoassignment.data.interfaces

import androidx.fragment.app.Fragment

interface ActivityHandler {

    fun showProgressBar()

    fun hideProgressBar()

    fun addFragment(fragment: Fragment,addToStack: Boolean)

    fun replaceFragment(fragment: Fragment,addToStack: Boolean)

    fun showError()
}