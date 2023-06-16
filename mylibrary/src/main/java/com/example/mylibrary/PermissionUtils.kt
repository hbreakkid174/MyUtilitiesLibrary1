package com.example.mylibrary

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


//single permission

fun Context.singlePermission(
    permissionString: String,
    onSinglePermissionGranted: () -> Unit,
    onSinglePermissionDenied: () -> Unit,
    onSinglePermissionError: (DexterError) -> Unit
) {
    Dexter.withContext(this).withPermissions(permissionString)
        .withListener(object : MultiplePermissionsListener {
            // ensure you implement members of the object which is related to dexter third party library
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if (report.areAllPermissionsGranted()) {
                        onSinglePermissionGranted()
                    } else {
                        onSinglePermissionDenied()
//                        showRationalDialogForPermissions()
                    }
                    if (report.isAnyPermissionPermanentlyDenied){
                        onSinglePermissionDenied()
                        showRationalDialogForPermissions()

                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?, token: PermissionToken?
            ) {
                // It is the alert dialog that user will allow permissions
                token?.continuePermissionRequest()
            }

        }).withErrorListener {
            onSinglePermissionError(it)
        }.check()
}

fun Context.multiPermission(
    permissionArray: List<String>,
    onMultiPermissionGranted: () -> Unit,
    onMultiPermissionDenied: () -> Unit,
    onMultiPermissionError: (DexterError) -> Unit
) {
    Dexter.withContext(this).withPermissions(permissionArray)
        .withListener(object : MultiplePermissionsListener {
            // ensure you implement members of the object which is related to dexter third party library
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if (report.areAllPermissionsGranted()) {
                        onMultiPermissionGranted()
                    } else {
                        onMultiPermissionDenied()
//                        showRationalDialogForPermissions()
                    }
                    if (report.isAnyPermissionPermanentlyDenied){
                        onMultiPermissionDenied()
                        showRationalDialogForPermissions()

                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?, token: PermissionToken?
            ) {
                // It is the alert dialog that user will allow permissions
                token?.continuePermissionRequest()
            }

        }).withErrorListener {
            onMultiPermissionError(it)
        }.check()
}

fun Context.showRationalDialogForPermissions() {
    AlertDialog.Builder(this).setMessage(
        "It looks that you have turned off " + "permissions required for these features. It can be enabled under " + "applications settings"
    ).setPositiveButton("Go To Settings") { _, _ ->
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
    }.show()
}

//check if permission granted or not
fun Context.checkPermissionGranted(permissionArray: List<String>): Boolean {
    val listPermissionsNeeded: ArrayList<String> = ArrayList()
    permissionArray.forEach { string ->
        val perm = ContextCompat.checkSelfPermission(this, string)
        if (perm != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(string)
        }

    }
    return listPermissionsNeeded.isEmpty()
}