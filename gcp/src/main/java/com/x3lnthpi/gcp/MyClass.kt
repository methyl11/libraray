package com.x3lnthpi.gcp

import com.google.cloud.vertexai.VertexAI
import com.google.cloud.vertexai.generativeai.GenerativeModel
import com.google.cloud.vertexai.api.GenerateContentResponse

class MyClass {
    fun printHello() {
        print("Hello, Duniya!")
    }
}


fun main(){
    val cl = MyClass()
    cl.printHello()
    val projectId = "astrolog-439912"
    val location = "us-central1"

    val prompt = "Write something on India"


    VertexAI(projectId, location).use { vertexAi ->
        val model = GenerativeModel("gemini-pro", vertexAi)
        val response: GenerateContentResponse = model.generateContent(prompt)
        // Do something with the response
        print("Response: $response")
    }



}