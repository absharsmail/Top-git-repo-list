package com.example.assignment_apnamart

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class AppPreferences(mContext: Context) {
    private val installed = mContext.packageManager.getPackageInfo(mContext.packageName, 0).firstInstallTime
    private val getSharedPref: SharedPreferences by lazy {
        mContext.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
    }
    private val editor: SharedPreferences.Editor = getSharedPref.edit()

    fun setLastUpdatedDate(value: Calendar?) {
        if (value != null) {
            val timeInMillis = value.timeInMillis
            editor.putLong(LAST_UPDATED_DATE, timeInMillis)
            editor.commit()
        }
    }

    fun getLastUpdatedDate(): Calendar {
        val timeInMillis = getSharedPref.getLong(LAST_UPDATED_DATE, installed)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        return calendar
    }

    companion object {
        private const val PREF_FILE= "RehabPreferences"
        private const val LAST_UPDATED_DATE = "Last Updated Date"
    }
}