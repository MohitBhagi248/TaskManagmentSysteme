package com.example.taskmanagement.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


class Prefs @SuppressLint("CommitPrefEdits") internal constructor(context: Context) {
    fun save(key: String?, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun save(key: String?, value: String?) {
        editor.putString(key, value).apply()
    }

    fun save(key: String?, value: Int) {
        editor.putInt(key, value).apply()

    }

    fun save(key: String?, value: Float) {
        editor.putFloat(key, value).apply()
    }

    fun save(key: String?, value: Long) {
        editor.putLong(key, value).apply()
    }

    fun save(key: String?, value: Set<String?>?) {
        editor.putStringSet(key, value).apply()
    }

    fun save(key: String?, value: Double) {
        editor.putLong(key, java.lang.Double.doubleToRawLongBits(value)).apply()
    }

    fun getBoolean(key: String?, defValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defValue)
    }

    fun getString(key: String?, defValue: String? = ""): String? {
        return preferences.getString(key, defValue)
    }

    fun getInt(key: String?, defValue: Int = 0): Int {
        return preferences.getInt(key, defValue)
    }

    fun getFloat(key: String?, defValue: Float = 0f): Float {
        return preferences.getFloat(key, defValue)
    }

    fun getDouble(key: String?, defaultValue: Double = 0.0): Double {
        return java.lang.Double.longBitsToDouble(
            preferences.getLong(
                key,
                java.lang.Double.doubleToLongBits(defaultValue)
            )
        )
    }

    fun getLong(key: String?, defValue: Long = 0): Long {
        return preferences.getLong(key, defValue)
    }

    fun getStringSet(
        key: String?,
        defValue: Set<String?>?
    ): Set<String>? {
        return preferences.getStringSet(key, defValue)
    }

    val all: Map<String, *>
        get() = preferences.all


    fun remove(key: String?) {
        editor.remove(key).apply()
    }

    fun removeAll() {
        editor.clear()
        editor.apply()

    }


    fun checkContains(key:String): Boolean {
        return preferences.contains(key)
    }

    private class Builder(context: Context?) {
        private val context: Context

        /**
         * Method that creates an instance of Prefs
         *
         * @return an instance of Prefs
         */
        fun build(): Prefs {
            return Prefs(context)
        }

        init {
            requireNotNull(context) { "Context must not be null." }
            this.context = context.applicationContext
        }
    }

    private var preferences: SharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = preferences.edit()



    companion object {
        private const val TAG = "UVADriverPreferences"
        private var singleton: Prefs? = null

        fun with(context: Context?): Prefs? {
            if (singleton == null) {
                singleton = Builder(context).build()
            }
            return singleton
        }



    }


}