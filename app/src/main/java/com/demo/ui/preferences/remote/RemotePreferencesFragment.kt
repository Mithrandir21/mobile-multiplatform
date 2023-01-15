package com.demo.ui.preferences.remote

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.demo.R
import com.demo.base.BasePreferenceFragment
import com.demo.common.config.RemoteConfig
import com.demo.ui.preferences.remote.RemotePreferencesViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemotePreferencesFragment : BasePreferenceFragment(R.xml.developer_preferences) {

    private val viewModel: RemotePreferencesViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferenceClickListener<Preference>(R.string.dev_preference_remote_environment_key) {
            viewModel.loadRemoteEnvironment()
        }



        viewModel.observeErrors().observe(viewLifecycleOwner) { (throwable, type) ->
            when (type) {
                ActionType.LoadingRemoteEnvironment -> throwable.printStackTrace()
                ActionType.SetRemoteEnvironment -> throwable.printStackTrace()
            }
        }

        viewModel.observeProgress().observe(viewLifecycleOwner) { (_, type) ->
            when (type) {
                ActionType.LoadingRemoteEnvironment -> Unit
                ActionType.SetRemoteEnvironment -> Unit
            }
        }

        viewModel.observeViewData().observe(viewLifecycleOwner) { data ->
            when (data) {
                is Data.PossibleRemoteEnvironments -> showPossibleRemoteEnvironments(data.possibles, data.currentIndex)
                Data.RemoteEnvironmentSet -> Toast.makeText(requireContext(), "Environment set. Please restart.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showPossibleRemoteEnvironments(possible: List<RemoteConfig>, chosenIndex: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dev_preference_remote_environment_selection_title)
            .setNeutralButton(R.string.dev_preference_remote_environment_selection_neutral, null)
            .setSingleChoiceItems(possible.map { it.configType.name.plus(": ".plus(it.baseUrl)) }.toTypedArray(), chosenIndex) { dialog, selectedIndex ->
                viewModel.setRemoteEnvironment(possible[selectedIndex])
                dialog.dismiss()
            }
            .show()
    }
}