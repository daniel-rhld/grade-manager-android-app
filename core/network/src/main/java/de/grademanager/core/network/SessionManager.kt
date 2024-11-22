package de.grademanager.core.network

import android.content.Context

private const val STORAGE_KEY = "de.grademanager.SessionHelper"

private const val KEY_ACCESS_TOKEN = "de.grademanager.SessionHelper.AccessToken"
private const val KEY_REFRESH_TOKEN = "de.grademanager.SessionHelper.RefreshToken"

class SessionManager(context: Context) {

    private val storage = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)

    fun getAccessToken(): String {
        return storage.getString(KEY_ACCESS_TOKEN, "") ?: ""
    }

    fun setAccessToken(token: String) {
        storage.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    fun getRefreshToken(): String {
        return storage.getString(KEY_REFRESH_TOKEN, "") ?: ""
    }

    fun setRefreshToken(token: String) {
        storage.edit().putString(KEY_REFRESH_TOKEN, token).apply()
    }

}