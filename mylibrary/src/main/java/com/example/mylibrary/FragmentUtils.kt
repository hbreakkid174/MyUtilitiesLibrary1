package com.example.mylibrary

import androidx.fragment.app.Fragment

fun Fragment.isFragmentAddAndDetach(addedOrDetachCallback: () -> Unit) {
    if (isAdded && !isDetached) {
        addedOrDetachCallback()
    }
}

