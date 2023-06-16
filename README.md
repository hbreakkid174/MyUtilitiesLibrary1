# MyUtilitiesLibrary
My Utility Library will help you in future with more hectic routine in development and work load one cannot save all their common functions, daily usage classes in same place with **MyUtilityLibrary** you can easily access all your common files at one place.

# Overview
1. [Installation](#Installation)
2. [Shared Preferences](#Shared-Preferences)
3. [Encrypted Shared Preferences](#Encrypted-Shared-Preferences)
4. [Permissions](#Permissions)
5. [Logs](#Logs) **(For logcat)**
6. Toasts

# Installation

**Step 1.** Add the JitPack repository to your build file <br/>
Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' 
		    }
		}
	}
```
	
**Step 2.** Add the dependency
```
	dependencies {
	        implementation 'com.github.hbreakkid174:MyUtilitiesLibrary:1.4-beta01'
	}
```

# Shared Preferences
Use preferences to fetch & save value of type **(String,Boolean,Int,Float,Long)** <br/>
**Note:** both these methods can be use in fragment & activities in the same way.<br/>
### To save data in preference
```kotlin
//for string
 setPref(key = "stringAsKey", defaultValue ="Saving String value in preference") 
 
 //for Int  (remember to replace with your own int value)
 setPref(key = "stringAsKey", defaultValue =0) 

//for boolean  (remember to replace with your own boolean value)
 setPref(key = "stringAsKey", defaultValue =false) 
 
```
### To get data from preference
```kotlin
//for string
  getPref(key = "stringAsKey", defaultValue = "string value as default") {
 //TODO: will return your string value from preference
   }
   
 //for int
  getPref(key = "stringAsKey", defaultValue =0) {
 //TODO: will return your inr value from preference
   }
 
 //for boolean
  getPref(key = "stringAsKey", defaultValue = false) {
 //TODO: will return your boolean value from preference
   }
```
### To save Model Class & ArrayList in preference
```kotlin
//To save arraylist in model of any kind
 setPref(key = "stringAsKey", defaultValue =listOf<TestModel>())

//To save model class in preference
val testModel = TestModel("example",true)
setPref(key = "stringAsKey",defaultValue =testModel)
```
### To get Model Class & ArrayList data from preference
```kotlin
//to fetch arraylist from preference
val list = myAppPreferences.getObject<ArrayList<TestModel>>("stringAsKey")
print(list)

//to fetch model class from preference
val modelClass= myAppPreferences.getObject<TestModel>("stringAsKey")
print(modelClass)
```

# Encrypted Shared Preferences
Use encrypted preferences to fetch & save value of type **(String,Boolean,Int,Float,Long)** more securely that all of that will be encrypted & that will help you save your data from being hacked when anyone try to apply reverse engineering on your code with **Encrypted Preferences** all of your data is saved.<br/>
**Note:** both these methods can be use in fragment & activities in the same way. And for **Fragment** use ``` context ``` or ``` requireContext() ``` follow with dot **(.)** & method<br/>
### To save data in preference
```kotlin
//for string
 saveEncryptedPrefValue(key = "stringAsKey", value ="Saving String value in preference") 
 
 //for Int  (remember to replace with your own int value)
 saveEncryptedPrefValue(key = "stringAsKey", value =0) 

//for boolean  (remember to replace with your own boolean value)
 saveEncryptedPrefValue(key = "stringAsKey", value =false) 
 
```
### To get data from preference
```kotlin
//for string
 val stringValue= getEncryptedPrefValue(key = "stringAsKey", value = "string value as default")
   
 //for int
  val intValue= getEncryptedPrefValue(key = "stringAsKey", value =0)
 
 //for boolean
  val booleanValue= getEncryptedPrefValue(key = "stringAsKey", value = false)
```

# Permissions
Android marshmallow and above SDK uses runtime permission to work with specific feature.If permission is deny there is an alternate dialog implemented that will send you to your app's setting screen where you can easily grant all your permissions. <br/>
For **Fragment** use ``` context ``` or ``` requireContext() ``` follow with dot **(.)** & method<br/>

### Single Permission
```kotlin
    singlePermission(permissionString = android.Manifest.permission.CAMERA,
                    onSinglePermissionGranted = {
                        //TODO: perform any action
                    },
                    onSinglePermissionDenied = {
                       //TODO: perform any action
                    },
                    onSinglePermissionError = {
                    //TODO: perform any action
                    })
```	    
### Multiple Permissions
```kotlin
 val permissionArray = listOf<String>(
        android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    
 multiPermission(permissionArray = permissionArray, onMultiPermissionGranted = {
                        //TODO: perform any action
                    }, onMultiPermissionDenied = {
                        //TODO: perform any action
                    }, onMultiPermissionError = {
                        //TODO: perform any action
                    })
```
### Check Permission Granted
```kotlin
val permissionArray = listOf<String>(
        android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    if (checkPermissionGranted(permissionArray = permissionArray)) {
    //permission granted successfully
    }
    else {
    // permission not granted
    }
```

# Logs
Logs for logcat is essential while developing an app & provides you ease in getting output of specifc warning, debug, error etc. value during development process. <br/>
In this library we have implemented logs in a manner that will help you in development more efficient way like remembering logcat **TAG** or generating TAG for a particular log value may sometime be hactic. We here with this library will automatically generate **TAG** for you. <br/>
**For Example** you are using log in ``` MainActivity ``` so the **TAG** will automatically generated with the name **MainActivity** which you can see in the **Logcat** section of your android studio. Moreover you can use these logcat method anywhere in fragments,activities,dialogs etc.

``` kotlin
//for debug logcat
debug("This is debug logcat")

//for warning logcat
warn("This is warning logcat")

//for error logcat
error("This is error logcat")
```
