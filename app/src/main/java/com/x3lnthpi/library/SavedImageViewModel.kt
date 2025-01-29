package com.x3lnthpi.library

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class SavedImageViewModel : ViewModel(){

    suspend fun getImagesFromFirestore(): List<ImageData> {
        val app = FirebaseApp.getInstance() // Get your FirebaseApp instance
        val db = Firebase.firestore(app, "stable-diffusion")
        val imagesCollection = db.collection("sd1.5")

        return try{
            val querySnapshot = imagesCollection.get().await()
            val imageList = mutableListOf<ImageData>()
            for(document in querySnapshot.documents){
                val imageUrl = document.getString("imageUrl")
                if(imageUrl != null){
                    val imageData = ImageData(imageUrl)
                    imageList.add(imageData)
                }
            }
            imageList
        } catch (e : Exception){
            e.printStackTrace()
            emptyList()
        }
    }

}

data class ImageData(val imageUrl : String)