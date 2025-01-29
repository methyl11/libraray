package com.x3lnthpi.library.views

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.storage.storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.ByteArrayOutputStream
import java.util.UUID

@Composable
fun PrintScreen(navController: NavController){

    // Get Remote Config values
    val remoteConfig = Firebase.remoteConfig
    val apiKey = remoteConfig.getString("Runpod_API_Keys")
    val endpointId = remoteConfig.getString("Runpod_Endpoint")

    Text("print Screen")
    Button(onClick = { navController.navigate("HomeScreen") }) {
        Text("Go to Home Screen")

    }
    RunPodApp(apiKey, endpointId)
}

// Decode base64 image
fun decodeBase64Image(base64Image: String): Bitmap? {
    val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun onImageDecoded(imageUrl: String) {

    val auth = Firebase.auth // Get Firebase Auth instance
    val currentUser = auth.currentUser // Get the current user

    if (currentUser != null){
        val app = FirebaseApp.getInstance() // Get your FirebaseApp instance
        val db = Firebase.firestore(app, "stable-diffusion")

        val imageData = hashMapOf(
            "imageUrl" to imageUrl,
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


@Composable
fun RunPodApp(apiKey: String, endpointId: String) {
    var userPrompt by remember { mutableStateOf("") }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var jobId by remember { mutableStateOf<String?>(null) }

    // Polling for job status
    LaunchedEffect(jobId) {
        if (jobId != null) {
            while (true) {
                delay(5000) // Poll every 5 seconds
                checkJobStatus(
                    endpointId = endpointId, // Your endpoint ID
                    apiKey = apiKey, // Your API key
                    jobId = jobId!!,
                    onImageDecoded = { bitmap ->
                        imageBitmap = bitmap
                        isLoading = false
                        jobId = null // Stop polling once the image is received
                    }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Input for user prompt
        BasicTextField(
            value = userPrompt,
            onValueChange = { userPrompt = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    if (userPrompt.isEmpty()) {
                        Text("Enter a prompt...")
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to send API request
        Button(
            onClick = {
                isLoading = true
                startJob(
                    endpointId = endpointId, // Your endpoint ID
                    apiKey = apiKey, // Your API key
                    prompt = userPrompt,
                    onJobStarted = { id ->
                        jobId = id // Start polling with the new job ID
                    }
                )
            },
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Loading..." else "Generate Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the generated image
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap!!.asImageBitmap(),
                contentDescription = "Generated Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        } else if (isLoading) {
            Text("Generating image...")
        }
    }
}

// Retrofit API setup
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.runpod.ai/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val runPodApi = retrofit.create(RunPodApi::class.java)

// API Interface
interface RunPodApi {
    @POST("v2/{endpointId}/run")
    fun startJob(
        @Path("endpointId") endpointId: String,
        @Body request: RunPodRequest,
        @Header("Authorization") apiKey: String
    ): Call<RunPodResponse>

    @GET("v2/{endpointId}/status/{jobId}")
    fun checkStatus(
        @Path("endpointId") endpointId: String,
        @Path("jobId") jobId: String,
        @Header("Authorization") apiKey: String
    ): Call<RunPodResponse>
}

// Data Classes
data class RunPodRequest(
    val input: Map<String, String>
)

data class RunPodResponse(
    val id: String,
    val status: String,
    val output: List<RunPodOutput>?
)

data class RunPodOutput(
    val image: String, // Base64 encoded image
    val seed: Int
)

// Start a job
fun startJob(endpointId: String, apiKey: String, prompt: String, onJobStarted: (String) -> Unit) {
    val request = RunPodRequest(
        input = mapOf("prompt" to prompt)
    )

    val call = runPodApi.startJob(endpointId, request, "Bearer $apiKey")
    call.enqueue(object : Callback<RunPodResponse> {
        override fun onResponse(call: Call<RunPodResponse>, response: Response<RunPodResponse>) {
            if (response.isSuccessful) {
                val jobId = response.body()?.id
                if (jobId != null) {
                    onJobStarted(jobId)
                }
            } else {
                println("Failed to start job: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<RunPodResponse>, t: Throwable) {
            t.printStackTrace()
        }
    })
}

// Check job status
fun checkJobStatus(endpointId: String, apiKey: String, jobId: String, onImageDecoded: (Bitmap) -> Unit) {



    val auth = Firebase.auth // Get Firebase Auth instance
    val currentUser = auth.currentUser // Get the current user


    val call = runPodApi.checkStatus(endpointId, jobId, "Bearer $apiKey")
    call.enqueue(object : Callback<RunPodResponse> {
        override fun onResponse(call: Call<RunPodResponse>, response: Response<RunPodResponse>) {
            if (response.isSuccessful) {
                val status = response.body()?.status
                val output = response.body()?.output

                if (status == "COMPLETED" && output != null) {
                    val base64Image = output[0].image
                    val bitmap = decodeBase64Image(base64Image)
                    if (bitmap != null) {
                        onImageDecoded(bitmap)




                        //Firebase storage logic
                        val storageRef = Firebase.storage.reference.child("generated_images")
                        val imageRef = storageRef.child("${UUID.randomUUID()}.jpg")

                        val baos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        val data = baos.toByteArray()
                        val uploadTask = imageRef.putBytes(data)
//                        uploadTask.addOnSuccessListener { snapshot ->
//                            // Image uploaded successfully
//                            snapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { imageUrl ->
//                                val imageUrl = snapshot.metadata?.reference?.downloadUrl.toString()
//                                onImageDecoded(imageUrl) // Pass the download URL to the callback
//                            }
//                        }
//                            .addOnFailureListener { exception ->
//                                // Handle upload failure
//                                exception.printStackTrace()
//                            }
                        uploadTask.addOnSuccessListener { snapshot ->
                            snapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri -> // Get the Uri
                                val imageUrl = uri.toString() // Convert Uri to String
                                onImageDecoded(imageUrl) // Now pass the String URL
                            }
                        }.addOnFailureListener { exception ->
                            exception.printStackTrace()
                        }
                    }
                } else {
                    println("Job status: $status")
                }
            } else {
                println("Failed to check job status: ${response.code()}")
            }

            // Firebase storage logic end
        }

        override fun onFailure(call: Call<RunPodResponse>, t: Throwable) {
            t.printStackTrace()
        }
    })
}

