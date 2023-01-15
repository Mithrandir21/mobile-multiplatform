package com.demo.testing

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import java.util.concurrent.ConcurrentHashMap

/**
 * [SharedPreferences] implementation used specifically for testing purposes to validate data placed into storage.
 */
class TestingSharedPreferences : SharedPreferences {

    private var onChangeListeners: MutableList<OnSharedPreferenceChangeListener> = ArrayList()
    private var mainMap: MutableMap<String, Any?> = ConcurrentHashMap()

    private val editor: SharedPreferences.Editor = TestingEditor(object : TestingEditorApplyAction {
        override fun apply(commitMap: Map<String, Any?>, removeList: List<String>, commitClear: Boolean) {

            val changedKey = mutableSetOf<String>()

            // clear
            if (commitClear) {
                mainMap.clear()
            }

            // Remove elements
            for (key in removeList) {
                mainMap.remove(key)
                changedKey.add(key)
            }

            // Add an element
            val keys: Set<String> = commitMap.keys

            // Change before and after comparison
            for (key in keys) {
                val lastValue = mainMap[key]
                val value = commitMap[key]

                if (lastValue == null && value != null || lastValue != null && value == null || lastValue != value) {
                    changedKey.add(key)
                }
            }

            mainMap.putAll(commitMap)


            // Notify all listeners of Key changes.
            for (listener in onChangeListeners) {
                for (key in changedKey) {
                    listener.onSharedPreferenceChanged(this@TestingSharedPreferences, key)
                }
            }
        }
    })


    override fun getAll(): MutableMap<String, Any?> = HashMap(mainMap)

    override fun getString(key: String, defValue: String?): String? =
        when (mainMap.containsKey(key)) {
            true -> mainMap[key] as String
            false -> defValue
        }

    @Suppress("UNCHECKED_CAST")
    override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? =
        when (mainMap.containsKey(key)) {
            true -> HashSet(mainMap[key] as Set<String>)
            false -> defValues
        }

    override fun getInt(key: String, defValue: Int): Int =
        when (mainMap.containsKey(key)) {
            true -> mainMap[key] as Int
            false -> defValue
        }

    override fun getLong(key: String, defValue: Long): Long =
        when (mainMap.containsKey(key)) {
            true -> mainMap[key] as Long
            false -> defValue
        }

    override fun getFloat(key: String, defValue: Float): Float =
        when (mainMap.containsKey(key)) {
            true -> mainMap[key] as Float
            false -> defValue
        }

    override fun getBoolean(key: String, defValue: Boolean): Boolean =
        when (mainMap.containsKey(key)) {
            true -> mainMap[key] as Boolean
            false -> defValue
        }

    override fun contains(key: String): Boolean = mainMap.containsKey(key)

    override fun edit(): SharedPreferences.Editor = editor

    override fun registerOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        onChangeListeners.add(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        onChangeListeners.remove(listener)
    }


    /**
     * Action interface for when the [TestingEditor] wishes to Commit/Apply the needed changes.
     *
     * The [apply] action is then performed inside the [TestingSharedPreferences], adding, removing or clearing values.
     */
    interface TestingEditorApplyAction {
        fun apply(commitMap: Map<String, Any?>, removeList: List<String>, commitClear: Boolean)
    }


    class TestingEditor(private val applyAction: TestingEditorApplyAction) : SharedPreferences.Editor {

        // Command to clear list
        private var commitClear = false

        /** Key/Object to be added map. */
        private var addMap: MutableMap<String, Any?> = ConcurrentHashMap()

        /** Keys to be removed list. */
        private var removeList: MutableList<String> = ArrayList()


        override fun putString(key: String, value: String?): SharedPreferences.Editor {
            addMap[key] = value
            return this
        }

        override fun putStringSet(key: String, values: MutableSet<String>?): SharedPreferences.Editor {
            addMap[key] = values
            return this
        }

        override fun putInt(key: String, value: Int): SharedPreferences.Editor {
            addMap[key] = value
            return this
        }

        override fun putLong(key: String, value: Long): SharedPreferences.Editor {
            addMap[key] = value
            return this
        }

        override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
            addMap[key] = value
            return this
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
            addMap[key] = value
            return this
        }

        override fun remove(key: String): SharedPreferences.Editor {
            addMap.remove(key)
            removeList.add(key)
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            commitClear = true
            addMap.clear()
            removeList.clear()
            return this
        }

        override fun commit(): Boolean {
            return try {
                apply()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        override fun apply() {
            applyAction.apply(addMap.toMap(), removeList, commitClear)

            // Empty the cached data each time you submit
            commitClear = false
            addMap.clear()
            removeList.clear()
        }

    }
}