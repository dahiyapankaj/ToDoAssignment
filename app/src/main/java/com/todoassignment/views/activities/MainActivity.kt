package com.todoassignment.views.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.todoassignment.R
import com.todoassignment.data.interfaces.ActivityHandler
import com.todoassignment.views.fragments.ItemListingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ActivityHandler {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addReplaceFragment(true, ItemListingFragment.newInstance(), false)
    }

    /**
     * Function which adds or replaces fragment
     *
     * @param add            boolean variable to decide whether to add or replace a fragment
     * @param fragment       is object of the fragment to be added
     * @param addToBackStack boolean to decide whether we have to add fragment to backstack or not
     */
    private fun addReplaceFragment(
        add: Boolean,
        fragment: Fragment,
        addToBackStack: Boolean
    ) {
        val fragmentManager = supportFragmentManager
        if (isFragmentInBackstack(fragmentManager, fragment.javaClass.simpleName)) {
            fragmentManager.popBackStack(fragment.javaClass.simpleName, 0)
        } else {
            val fragmentTransaction =
                fragmentManager.beginTransaction()
            if (add) {
                fragmentTransaction.add(
                    R.id.fl_container,
                    fragment,
                    fragment.javaClass.simpleName
                )
            } else {
                fragmentTransaction.replace(
                    R.id.fl_container,
                    fragment,
                    fragment.javaClass.simpleName
                )
            }
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
            }
            fragmentTransaction.commitAllowingStateLoss()
        }
    }


    /**
     * Function to replace a fragment
     */


    override fun showProgressBar() {
        include_progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        include_progressbar.visibility = View.GONE
    }

    override fun addFragment(fragment: Fragment, addToStack: Boolean) {
        addReplaceFragment(true, fragment, addToStack)
    }

    override fun replaceFragment(fragment: Fragment, addToStack: Boolean) {
        addReplaceFragment(false, fragment, addToStack)
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     * Method to check if the given fragment is already in backstack or not.
     *
     * @param fragmentManager object of fragment manager
     * @param tag             is the tag geiven to fragment
     * @return true if fragment exists in backstack
     */
    private fun isFragmentInBackstack(
        fragmentManager: FragmentManager,
        tag: String
    ): Boolean {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            if (tag == fragmentManager.getBackStackEntryAt(i).name) {
                return true
            }
        }
        return false
    }

    /**
     * Overridden  function for device back press
     */
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            showExitDialog()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Function to seek confirmation before exit app
     */
    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.exit_title))
            .setMessage(getString(R.string.exit_body))
            .setPositiveButton(
                getString(R.string.exit_yes)
            ) { dialog: DialogInterface?, which: Int ->
                // Exit Application
                finish()
            }
            .setNegativeButton(
                getString(R.string.exit_No)
            ) { dialog: DialogInterface, which: Int ->
                // just dismiss the dialog
                dialog.dismiss()
            }
            .show()
    }
}
