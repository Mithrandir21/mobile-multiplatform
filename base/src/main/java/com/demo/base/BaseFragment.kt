package com.demo.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(@LayoutRes layout: Int) : LoggingBaseFragment(layout) {

    lateinit var viewBinding: T

    /**
     * Attempts to return the desired (type [VM]) [ViewModel].
     *
     * If the given [VM] [ViewModel] cannot be provided, a [RuntimeException] will be thrown.
     */
    protected inline fun <reified VM : ViewModel> viewModel(): VM = ViewModelProvider(this).get(VM::class.java)

    protected fun getBaseActivity(): BaseActivity<*> = requireNotNull(requireActivity() as BaseActivity<*>) { "Activity must not be null!" }

    protected val navController: NavController by lazy { getBaseActivity().navController }
}