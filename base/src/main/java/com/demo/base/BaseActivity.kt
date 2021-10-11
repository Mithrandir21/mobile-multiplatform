package com.demo.base

import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : LoggingBaseActivity() {

    lateinit var viewBinding: T

    /** Main navigation controller associated with this Activity. MUST be initialized in the sub-class. */
    abstract val navController: NavController

    /**
     * Attempts to return the desired (type [VM]) [ViewModel].
     *
     * If the given [VM] [ViewModel] cannot be provided, a [RuntimeException] will be thrown.
     */
    protected inline fun <reified VM : ViewModel> viewModel(): VM = ViewModelProvider(this).get(VM::class.java)

}

/**
 * Simplifies the retrieval of the NavController.
 *
 * See: https://issuetracker.google.com/issues/142847973
 */
fun BaseActivity<*>.getNavController(@IdRes navControllerId: Int) =
    supportFragmentManager.findFragmentById(navControllerId).let { it as NavHostFragment }.navController