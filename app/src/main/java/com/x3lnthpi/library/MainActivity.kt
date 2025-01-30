package com.x3lnthpi.library


import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.x3lnthpi.library.ui.theme.LibraryTheme
import android.Manifest
import android.app.AlertDialog
import androidx.annotation.RequiresApi


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("Notification Permission Needed")
            .setMessage("This app needs notification permission to alert you about important updates.")
            .setPositiveButton("OK") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton("No Thanks", null)
            .show()
    }

    //For firebase Messaging Permission
    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
                showPermissionRationaleDialog()
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryTheme {

                AppNavigation()

//                val auth = Firebase.auth // Get Firebase Auth instance
//                val currentUser = auth.currentUser // Get the current user
//
//                print("current user is  $currentUser")
//
//                if (currentUser != null){
//                    val app = FirebaseApp.getInstance() // Get your FirebaseApp instance
//                    val db = Firebase.firestore(app, "stable-diffusion")
//
//                    val imageData = hashMapOf(
//                        "imageUrl" to "Some URL",
//                        //"prompt" to userPrompt,
//                        "createdAt" to FieldValue.serverTimestamp()
//                    )
//
//                    val imagesRef = db.collection("sd1.5")
//
//                    imagesRef.add(imageData)
//                        .addOnSuccessListener { documentReference ->
//                            // Image metadata stored successfully
//                            println("Added to firestore successful")
//                        }
//                        .addOnFailureListener { exception ->
//                            // Handle error while storing metadata
//                            exception.printStackTrace()
//                            Log.w(TAG, "Error adding document", exception)
//                            print("Firestore addition failed")
//                        }
//                } else {
//                    // Handle unauthenticated user (e.g., redirect to login)
//                    println("User is not authenticated. Cannot add image to Firestore.")
//                }


            }

        }
    }
}





