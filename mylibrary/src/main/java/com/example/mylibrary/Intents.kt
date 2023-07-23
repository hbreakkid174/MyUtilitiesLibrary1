package com.example.mylibrary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat


fun Context.openUrl(browserUrl: String, onLinkNotFoundError: (() -> Unit)? = null) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(browserUrl)
        })
    } catch (e: Exception) {
        e.printStackTrace()
        onLinkNotFoundError?.invoke()
    }
}

fun Context.rateUsApp(packageNameApp: String, onLinkNotFoundError: (() -> Unit)? = null) {

    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageNameApp"))
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        // If the Play Store app is not available on the device, open the Play Store website
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
            startActivity(intent)
        } catch (e: Exception) {
            onLinkNotFoundError?.invoke()
        }
    }

}

fun Context.moreApps(playStoreAccountName: String, onLinkNotFoundError: (() -> Unit)? = null) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data =
                Uri.parse("https://play.google.com/store/apps/developer?id=$playStoreAccountName")
        })
    } catch (e: Exception) {
        e.printStackTrace()
        onLinkNotFoundError?.invoke()
    }
}

fun Context.shareApp(packageNameApp: String, onLinkNotFoundError: (() -> Unit)? = null) {
    try {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=$packageNameApp"
            )
            type = "text/plain"
        }

        startActivity(sendIntent)
    } catch (e: Exception) {
        e.printStackTrace()
        onLinkNotFoundError?.invoke()
    }
}

fun Context.feedbackEmail(
    email: String,
    subject: String,
    body: String,
    onLinkNotFoundError: (() -> Unit)? = null
) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        startActivity(Intent.createChooser(intent, "Send Via"))
    } catch (e: Exception) {
        e.printStackTrace()
        onLinkNotFoundError?.invoke()
    }
}

inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}
/*
Simple using:

startActivity<MainActivity>()

With extra

startActivity<MainActivity>{
    putExtra("param 1", "Simple")
}
*/

fun Context.shareText(textToShare: String, onLinkNotFoundError: (() -> Unit)? = null) {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, textToShare)
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    } catch (e: Exception) {
        e.printStackTrace()
        onLinkNotFoundError?.let { it() }
    }

}