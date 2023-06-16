package com.example.myutilitieslibrary

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.checkPermissionGranted
import com.example.mylibrary.debug
import com.example.mylibrary.getEncryptedPrefValue
import com.example.mylibrary.getObject
import com.example.mylibrary.getPref
import com.example.mylibrary.multiPermission
import com.example.mylibrary.myAppPreferences
import com.example.mylibrary.saveEncryptedPrefValue
import com.example.mylibrary.setPref
import com.example.mylibrary.singlePermission
import com.example.mylibrary.toastShort
import com.example.myutilitieslibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    val permissionArray = listOf<String>(
        android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private var testModelList: ArrayList<TestModel> = ArrayList()

    fun getList() {
        testModelList.add(TestModel("testing", false))
        testModelList.add(TestModel("testing2", false))
        testModelList.add(TestModel("testing3", true))
        testModelList.add(TestModel("testing4", true))
    }

    companion object {
        const val TAG = "MainActivity1"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        getList()
        //TODO: for preference
        sharePreferenceUtilCheck()

        //TODO: for permission
        permissionUtilCheck()

        //TODO: for toast
        toastFunction()

        //TODO: for encrypted preference
        encryptedPrefCheck()

        //TODO: save & get arraylist from preference
        debug("testModelList: ${testModelList.size}")
        saveAndGetListFromPref()

        //TODO; save & get model class from preference
        saveAndGetModelClassFromPref()
    }

    private fun saveAndGetModelClassFromPref() {
        val testModel = TestModel("modelclass",true)
        setPref("testModel",testModel)
        debug("get model: ${myAppPreferences.getObject<TestModel>("testModel")}")
    }

    private fun saveAndGetListFromPref() {
        setPref("setArrayList", testModelList)
        debug("setArrayList: ${myAppPreferences.getObject<ArrayList<TestModel>>("setArrayList")}")
        val list = myAppPreferences.getObject<ArrayList<TestModel>>("setArrayList")
        debug("list from pref: $list")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun encryptedPrefCheck() {
        saveEncryptedPrefValue("testing", 23)
        saveEncryptedPrefValue("testing1", "2ad23")

        debug(getEncryptedPrefValue("testing", 0).toString())
        debug(getEncryptedPrefValue("testing1", "").toString())
    }

    private fun toastFunction() {
        binding?.apply {
            showToast.setOnClickListener {
                toastShort("testing it")
            }
        }
    }

    private fun permissionUtilCheck() {
        binding?.apply {
            //for single permission
            singlePermission.setOnClickListener {
                singlePermission(permissionString = android.Manifest.permission.CAMERA,
                    onSinglePermissionGranted = {
                        debug("onSinglePermissionGranted")
                    },
                    onSinglePermissionDenied = {
                        debug("onSinglePermissionDenied")
                    },
                    onSinglePermissionError = {
                        debug("onSinglePermissionError:")
                    })
            }
            //for multiPermission
            multiPerm.setOnClickListener {
                if (checkPermissionGranted(permissionArray = permissionArray)) {
                    debug("checkPermissionGranted")
                } else {
                    multiPermission(permissionArray = permissionArray, onMultiPermissionGranted = {
                        debug("onMultiPermissionGranted")
                    }, onMultiPermissionDenied = {
                        debug("onMultiPermissionDenied")
                    }, onMultiPermissionError = {
                        debug("onMultiPermissionError:")
                    })
                }
            }
        }
    }


    private fun sharePreferenceUtilCheck() {
        binding?.apply {
            //save preferences
            savePref.setOnClickListener {
                setPref(TAG, 33)
            }
            //view value from pref
            viewPref.setOnClickListener {
                getPref(key = TAG, defaultValue = 1) {
                    debug(it.toString())
                }
            }

        }
    }
}