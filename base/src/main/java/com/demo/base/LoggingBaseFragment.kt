package com.demo.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.demo.logging.Logger
import javax.inject.Inject

abstract class LoggingBaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    @Inject
    lateinit var logger: Logger

    override fun onAttach(context: Context) {
        super.onAttach(context)
        logger.log { "${this::class.java} - onAttach" }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.log { "${this::class.java} - onCreate" }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logger.log { "${this::class.java} - onCreateView" }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logger.log { "${this::class.java} - onViewCreated" }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        logger.log { "${this::class.java} - onStart" }
        super.onStart()
    }

    override fun onResume() {
        logger.log { "${this::class.java} - onResume" }
        super.onResume()
    }

    override fun onPause() {
        logger.log { "${this::class.java} - onPause" }
        super.onPause()
    }

    override fun onStop() {
        logger.log { "${this::class.java} - onStop" }
        super.onStop()
    }

    override fun onDestroyView() {
        logger.log { "${this::class.java} - onDestroyView" }
        super.onDestroyView()
    }

    override fun onDestroy() {
        logger.log { "${this::class.java} - onDestroy" }
        super.onDestroy()
    }

    override fun onDetach() {
        logger.log { "${this::class.java} - onDetach" }
        super.onDetach()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        logger.log { "${this::class.java} - onConfigurationChanged" }
        super.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        logger.log { "${this::class.java} - onSaveInstanceState" }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        logger.log { "${this::class.java} - onViewStateRestored" }
        super.onViewStateRestored(savedInstanceState)
    }
}