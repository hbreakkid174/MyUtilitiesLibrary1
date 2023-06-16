package com.example.mylibrary

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

@RequiresApi(Build.VERSION_CODES.M)
val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

//create encrypt preference
val Context.myEncryptedSharedPreferences: SharedPreferences
    @RequiresApi(Build.VERSION_CODES.M)
    get() {
        return EncryptedSharedPreferences.create(
            packageName, // fileName
            masterKeyAlias, // masterKeyAlias
            this, // context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // prefKeyEncryptionScheme
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // prefvalueEncryptionScheme
        )
    }


@RequiresApi(Build.VERSION_CODES.M)
fun Context.saveEncryptedPrefValue(key: String, value: Any) {
    with(myEncryptedSharedPreferences.edit()) {
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
        }
        commit()
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.getEncryptedPrefValue(key: String, value: Any): Any {
    with(myEncryptedSharedPreferences) {
        return when (value) {
            is Int -> getInt(key, value)
            is Long -> getLong(key, value)
            is Float -> getFloat(key, value)
            is String -> getString(key, value)
            is Boolean -> getBoolean(key, value)
            else -> {

            }
        }!!
    }
}


