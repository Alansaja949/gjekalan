package com.example.gojekaja.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString("auth_token", null)
    }
}