package com.demo.ui.preferences.app

import com.demo.R
import com.demo.base.BasePreferenceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPreferencesFragment : BasePreferenceFragment(R.xml.main_preference) {

    private val viewModel: MainPreferencesViewModel by lazy { viewModel() }

}