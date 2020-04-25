package com.newsapp.services

import android.util.Log
import com.newsapp.constants.API_KEY
import okhttp3.*

import org.json.JSONObject

import java.lang.Exception

import kotlin.collections.HashMap

class RequestTypes {
    companion object {
        val GET = "GET"
        val POST = "POST"
        val PUT = "PUT"
        val DELETE = "DELETE"
    }
}

class ApiService(var url: String, var method: String = RequestTypes.GET, var body: HashMap<String, Any> = HashMap()) {

    fun execute(): JSONObject {

        val client = OkHttpClient()

        url += "?"

        body["apiKey"] = API_KEY

        var request = Request.Builder()

        request = if (method == RequestTypes.GET) {

            for((k, v) in body) {
                url += "$k=$v&"
            }
            url = url.dropLast(1)

            Log.d("ApiService", "Get to $url ==============")
            request.url(url)

        } else {
            Log.d("ApiService", "$method to $url ==============")

            request.url(url).method(
                method,
                RequestBody.create(null, JSONObject(body.toMap()).toString())
            )
        }

        return try {
            val response = client.newCall(request.build()).execute()

            JSONObject(response.body()?.string())
        } catch (e: Exception) {
            Log.d("ApiService: Error", "${e.message}")

            JSONObject("{\"error\": \"${e.message}\"}")
        }


    }
}