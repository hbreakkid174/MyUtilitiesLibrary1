package com.example.mylibrary

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


//for normal use
val Context.myAppPreferences: SharedPreferences
    get() = getSharedPreferences(
        this.packageName, MODE_PRIVATE
    )

//for fragment
val Fragment.myAppPreferences: () -> SharedPreferences
    get() = {
        context?.getSharedPreferences(
            "${context?.packageName}", MODE_PRIVATE
        )!!
    }

//set value to preference in context
inline fun <reified T : Any> Context.setPref(key: String, defaultValue: T) {
    myAppPreferences[key] = defaultValue
}

//set value to preference in fragment
inline fun <reified T : Any> Fragment.setPref(key: String, defaultValue: T) {
    context?.myAppPreferences?.set(key, defaultValue)
}

//get value context
inline fun <reified T : Any> Context.getPref(
    key: String, defaultValue: T, returnValue: (T) -> Unit
) {
    val value = myAppPreferences.get(key, defaultValue)
    returnValue(value)
}

//get value fragment

inline fun <reified T : Any> Fragment.getPref(
    key: String, defaultValue: T, returnValue: (T) -> Unit
) {
    val value = context?.myAppPreferences?.get(key, defaultValue)
    value?.let { returnValue(it) }
}

inline fun <reified T : Any> SharedPreferences.getObject(key: String): T? {
    return Gson().fromJson<T>(getString(key, null), T::class.java)
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T {
    return when (T::class) {
        Boolean::class -> getBoolean(key, defaultValue as? Boolean? ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float? ?: 0.0f) as T
        Int::class -> getInt(key, defaultValue as? Int? ?: 0) as T
        Long::class -> getLong(key, defaultValue as? Long? ?: 0L) as T
        String::class -> getString(key, defaultValue as? String? ?: "") as T
        else -> {
            if (defaultValue is Set<*>) {
                getStringSet(key, defaultValue as Set<String>) as T
            } else {
                val typeName = T::class.java.simpleName
                throw Error("Unable to get shared preference with value type '$typeName'. Use getObject")
            }
        }
    }
}


@Suppress("UNCHECKED_CAST")
inline operator fun <reified T : Any> SharedPreferences.set(key: String, value: T) {
    with(edit()) {
        when (T::class) {
            Boolean::class -> putBoolean(key, value as Boolean)
            Float::class -> putFloat(key, value as Float)
            Int::class -> putInt(key, value as Int)
            Long::class -> putLong(key, value as Long)
            String::class -> putString(key, value as String)
            else -> {
                if (value is Set<*>) {
                    putStringSet(key, value as Set<String>)
                } else {
                    val json = Gson().toJson(value)
                    putString(key, json)
                }
            }
        }
        commit()
    }
}