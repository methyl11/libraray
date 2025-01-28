package com.x3lnthpi.library


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.x3lnthpi.library.ui.theme.LibraryTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryTheme {

                AppNavigation()


                val auth = Firebase.auth // Get Firebase Auth instance
                val currentUser = auth.currentUser // Get the current user

                print("current user is  $currentUser")

                if (currentUser != null){
                    val app = FirebaseApp.getInstance() // Get your FirebaseApp instance
                    val db = Firebase.firestore(app, "stable-diffusion")

                    val imageData = hashMapOf(
                        "imageUrl" to "Some URL",
                        //"prompt" to userPrompt,
                        "createdAt" to FieldValue.serverTimestamp()
                    )

                    val imagesRef = db.collection("sd1.5")

                    imagesRef.add(imageData)
                        .addOnSuccessListener { documentReference ->
                            // Image metadata stored successfully
                            println("Added to firestore successful")
                        }
                        .addOnFailureListener { exception ->
                            // Handle error while storing metadata
                            exception.printStackTrace()
                            Log.w(TAG, "Error adding document", exception)
                            print("Firestore addition failed")
                        }
                } else {
                    // Handle unauthenticated user (e.g., redirect to login)
                    println("User is not authenticated. Cannot add image to Firestore.")
                }


            }

        }
    }
}





