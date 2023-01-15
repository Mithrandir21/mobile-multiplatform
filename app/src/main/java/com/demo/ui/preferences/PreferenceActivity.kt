package com.demo.ui.preferences

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.demo.R
import com.demo.base.LoggingBaseActivity
import com.demo.ui.preferences.app.MainPreferencesFragment
import dagger.hilt.android.AndroidEntryPoint


/*
 * Stand alone activity for Preferences to allow better encapsulation, especially in relation to preference navigation.
 */
@AndroidEntryPoint
class PreferenceActivity : LoggingBaseActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        // If not Saved bundle exists, it is considered the first start.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.preferenceContainer, MainPreferencesFragment())
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .commit()
        }
    }

    /**
     * Called when the user has clicked on a preference that has a fragment class name
     * associated with it. The implementation should instantiate and switch to an instance
     * of the given fragment.
     *
     * @param caller The fragment requesting navigation
     * @param pref   The preference requesting the fragment
     * @return {@code true} if the fragment creation has been handled
     */
    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat, pref: Preference): Boolean {
        /*
         * NOTE: Current recommended approach as current Preferences functionality does not support Navigation Components.
         * https://developer.android.com/guide/topics/ui/settings/organize-your-settings
         */

        // Instantiate the new Fragment
        val args = pref.extras
        val fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, pref.fragment)
        fragment.arguments = args
        @Suppress("DEPRECATION")
        fragment.setTargetFragment(caller, 0)

        // Replace the existing Fragment with the new Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.preferenceContainer, fragment)
            .addToBackStack(null)
            .commit()

        return true
    }


    override fun finish() {
        super.finish()
        // Overrides the Exit transition of the Activity
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}