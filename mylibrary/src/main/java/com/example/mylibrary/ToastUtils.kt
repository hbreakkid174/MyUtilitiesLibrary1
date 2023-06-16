package com.example.mylibrary

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment


private var toast: Toast? = null

//for short toast
fun Context.toastShort(message: String) {
    if (toast != null) {
        toast?.cancel()
        toast = null
    }
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast?.show()
}
//for long toast
fun Context.toastLong(message: String) {
    if (toast != null) {
        toast?.cancel()
        toast = null
    }
    toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
    toast?.show()
}

//for short toast Fragment
fun Fragment.toastShort(message: String) {
    if (toast != null) {
        toast?.cancel()
        toast = null
    }
    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast?.show()
}

//for long toast Fragment
fun Fragment.toastLong(message: String) {
    if (toast != null) {
        toast?.cancel()
        toast = null
    }
    toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast?.show()
}

//run this method if you want to release toast from memory explicitly if not garbage collector will handle it automatically
fun cancelToast() {
    if (toast != null) {
        toast?.cancel()
        toast = null
    }
}