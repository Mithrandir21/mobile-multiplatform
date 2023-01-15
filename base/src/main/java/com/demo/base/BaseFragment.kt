package com.demo.base

import androidx.annotation.LayoutRes
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFragment<T : ViewBinding>(@LayoutRes layout: Int) : LoggingBaseFragment(layout) {

    lateinit var viewBinding: T

    /**
     * [CompositeDisposable] for any and all [Disposable]s used in the Fragment.
     * Disposed when [onDestroy] is called.
     */
    private val compositeDisposable = CompositeDisposable()

    protected fun getBaseActivity(): BaseActivity<*> = requireNotNull(requireActivity() as BaseActivity<*>) { "Activity must not be null!" }

    protected val navController: NavController by lazy { getBaseActivity().navController }


    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    protected fun Disposable.addToDisposable() {
        compositeDisposable.add(this)
    }
}