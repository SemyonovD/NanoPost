package com.example.nanopost.data.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PreferencesRepository {

    override suspend fun addToken(token: String?) {
        val editor = sharedPreferences.edit()
        if (token == null) {
            editor.remove(PREF_TOKEN)
        } else {
            editor.putString(PREF_TOKEN, token)
        }
        editor.apply()
    }

    override fun getToken(): String? = sharedPreferences.getString(PREF_TOKEN, null)

    override suspend fun addProfileId(id: String?) {
        val editor = sharedPreferences.edit()
        if (id == null) {
            editor.remove(PREF_PROFILEID)
        } else {
            editor.putString(PREF_PROFILEID, id)
        }
        editor.apply()
    }

    override suspend fun getProfileId(): String? = sharedPreferences.getString(PREF_PROFILEID, null)

    companion object {
        private const val PREF_TOKEN = "token"
        private const val PREF_PROFILEID = "profileid"
    }

}