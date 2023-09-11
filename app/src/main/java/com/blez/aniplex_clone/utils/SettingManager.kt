package com.blez.aniplex_clone.utils

import android.content.Context
import android.content.SharedPreferences
import com.blez.aniplex_clone.utils.Constants.PREFS_TOKEN_FILE
import com.blez.aniplex_clone.utils.Constants.RECOMMENDATION
import com.blez.aniplex_clone.utils.Constants.USER_VIDEO_PREFERENCE

class SettingManager(context: Context) {
    private var settingPrefs: SharedPreferences = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveVideoPreference(videoPref: String) {
        val editor = settingPrefs.edit()
        editor.putString(USER_VIDEO_PREFERENCE, videoPref)
        editor.apply()
    }
    fun getVideoPrefs() : String?
    {
        return settingPrefs.getString(USER_VIDEO_PREFERENCE,null)

    }
    fun deteleCredit(){
        if(settingPrefs.getString(USER_VIDEO_PREFERENCE,null) != null )
        {
            val editor = settingPrefs.edit()
            editor.clear()
            editor.apply()
        }

    }

    fun saveSuggestion(anime : String){
        val editor = settingPrefs.edit()
        editor.putString(RECOMMENDATION, anime)
        editor.apply()
    }
    fun getSuggestion() : String{
        return settingPrefs.getString(RECOMMENDATION,"Dr. Stone").toString()
    }


}