package com.x3lnthpi.library.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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
import com.google.firebase.remoteconfig.remoteConfig
import kotlinx.coroutines.delay
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
                    }
                } else {
                    println("Job status: $status")
                }
            } else {
                println("Failed to check job status: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<RunPodResponse>, t: Throwable) {
            t.printStackTrace()
        }
    })
}

// Decode base64 image
fun decodeBase64Image(base64Image: String): Bitmap? {
    val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}