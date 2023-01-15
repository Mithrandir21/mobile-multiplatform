package com.demo.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

abstract class BasePreferenceFragment(@XmlRes val pref: Int) : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(pref, rootKey)
    }


    /** Simple function that guarantees a [Preference] of given type [T] OR throws an exception. */
    protected fun <T : Preference> findPreferenceType(@StringRes key: Int): T = findPreferenceType(getString(key))

    /** Simple function that guarantees a [Preference] of given type [T] OR throws an exception. */
    protected fun <T : Preference> findPreferenceType(key: CharSequence): T =
        findPreference(key) ?: throw RuntimeException("Preference for key ('$key') was not found.")

    /** Simplify the functionality surrounding preference click listeners. */
    protected fun <T : Preference> preferenceClickListener(@StringRes key: Int, action: (T) -> Unit) = preferenceClickListener(getString(key), action)

    /** Simplify the functionality surrounding preference click listeners. */
    protected fun <T : Preference> preferenceClickListener(key: CharSequence, action: (T) -> Unit) {
        findPreferenceType<T>(key).setOnPreferenceClickListener {
            @Suppress("UNCHECKED_CAST") // This cast is logically guaranteed, but complicated because of the listener.
            action.invoke(it as T)

            return@setOnPreferenceClickListener true
        }
    }
}