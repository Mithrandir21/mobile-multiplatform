package com.demo.base

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.logging.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class LoggingBaseActivity : AppCompatActivity() {

    @Inject
    lateinit var logger: Logger


    override fun onConfigurationChanged(newConfig: Configuration) {
        logger.log { "${this::class.java} - onConfigurationChanged" }
        super.onConfigurationChanged(newConfig)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.log { "${this::class.java} - onCreate #1 (savedInstanceState != null) = ${savedInstanceState != null}" }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        logger.log { "${this::class.java} - onCreate #2 (savedInstanceState != null) = ${savedInstanceState != null}" }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        logger.log { "${this::class.java} - onPostCreate #1 (savedInstanceState != null) = ${savedInstanceState != null}" }
        super.onPostCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        logger.log { "${this::class.java} - onPostCreate #2 (savedInstanceState != null) = ${savedInstanceState != null}" }
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        logger.log { "${this::class.java} - onStart" }
        super.onStart()
    }

    override fun onResume() {
        logger.log { "${this::class.java} - onResume" }
        super.onResume()
    }

    override fun onPostResume() {
        logger.log { "${this::class.java} - onPostResume" }
        super.onPostResume()
    }

    override fun onPause() {
        logger.log { "${this::class.java} - onPause" }
        super.onPause()
    }

    override fun onStop() {
        logger.log { "${this::class.java} - onStop" }
        super.onStop()
    }

    override fun onDestroy() {
        logger.log { "${this::class.java} - onDestroy" }
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        logger.log { "${this::class.java} - onSaveInstanceState" }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        logger.log { "${this::class.java} - onRestoreInstanceState" }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onBackPressed() {
        logger.log { "${this::class.java} - onBackPressed" }
        super.onBackPressed()
    }

    override fun finish() {
        logger.log { "${this::class.java} - finish" }
        super.finish()
    }

    override fun finishAffinity() {
        logger.log { "${this::class.java} - finishAffinity" }
        super.finishAffinity()
    }

    override fun finishAndRemoveTask() {
        logger.log { "${this::class.java} - finishAndRemoveTask" }
        super.finishAndRemoveTask()
    }
}