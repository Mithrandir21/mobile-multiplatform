package com.demo.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.NavController
import com.demo.R
import com.demo.base.BaseActivity
import com.demo.base.getNavController
import com.demo.databinding.ActivityMainBinding
import com.demo.ui.preferences.PreferenceActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val navController: NavController
        get() = getNavController(R.id.mainNavHostFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Set the Navigation Graph in addition to data (if any) passed into this Activity, which is handed to the NavGraph start destination.
        navController.setGraph(R.navigation.main_nav_graph, intent.extras)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.preferences -> {
                val bundle = ActivityOptionsCompat
                    .makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
                    .toBundle()
                startActivity(Intent(this, PreferenceActivity::class.java), bundle)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun finish() {
        super.finish()
        // Overrides the Exit transition of the Activity
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}