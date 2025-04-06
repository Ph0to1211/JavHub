package com.jadesoft.javhub.data.preferences

import android.content.Context
import androidx.core.content.edit

class PreferencesManager(context: Context) {
    private val sharePres = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object Keys {
        // Style options
        const val EXPLORE_TYPE = "explore_type"
        const val ONLY_SHOW_MAG = "only_show_mag"
        const val SHOW_UNCENSORED = "show_uncensored"
        const val ITEM_STYLE = "item_style"
        const val ITEM_NUM = "item_num"

        // Record options
        const val USER_TAGS = "user_tags"

        // Setting options
        const val PUBLIC_MODE = "public_mode"
        const val STEALTH_MODE = "stealth_mode"
    }

    var exploreType: Boolean // true: 影片 false: 女优
        get() = sharePres.getBoolean(EXPLORE_TYPE, true)
        set(value) = sharePres.edit { putBoolean(EXPLORE_TYPE, value) }

    var onlyShowMag: Boolean
        get() = sharePres.getBoolean(ONLY_SHOW_MAG, false)
        set(value) = sharePres.edit { putBoolean(ONLY_SHOW_MAG, value) }

    var showUncensored: Boolean
        get() = sharePres.getBoolean(SHOW_UNCENSORED, false)
        set(value) {sharePres.edit { putBoolean(SHOW_UNCENSORED, value) }}

    var itemStyle: Int
        get() = sharePres.getInt(ITEM_STYLE, 0)
        set(value) {sharePres.edit { putInt(ITEM_STYLE, value) }}

    var itemNum: Int
        get() = sharePres.getInt(ITEM_NUM, 3)
        set(value) {sharePres.edit { putInt(ITEM_NUM, value)}}


    var userTags: String
        get() = sharePres.getString(USER_TAGS, "默认,新推出,热映,即将上映").toString()
        set(value) {sharePres.edit { putString(USER_TAGS, value) }}


    var publicMode: Boolean
        get() = sharePres.getBoolean(PUBLIC_MODE, false)
        set(value) = sharePres.edit { putBoolean(PUBLIC_MODE, value) }

    var stealthMode: Boolean
        get() = sharePres.getBoolean(STEALTH_MODE, false)
        set(value) = sharePres.edit { putBoolean(STEALTH_MODE, value) }
}