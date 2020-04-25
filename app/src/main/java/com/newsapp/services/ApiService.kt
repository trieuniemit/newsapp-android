package com.newsapp.services

import android.util.Log
import com.newsapp.constants.API_KEY
import com.newsapp.constants.API_URL
import com.newsapp.constants.RSS_URL
import com.newsapp.constants.WORLD_NEWS
import okhttp3.*
import org.json.JSONObject
import java.lang.Exception
import kotlin.collections.HashMap
import fr.arnaudguyon.xmltojsonlib.XmlToJson



class RequestTypes {
    companion object {
        val GET = "GET"
        val POST = "POST"
        val PUT = "PUT"
        val DELETE = "DELETE"
    }
}

class ApiService(var url: String, var method: String = RequestTypes.GET, var body: HashMap<String, Any> = HashMap()) {

    companion object {

        fun readRss(url: String): JSONObject? {
            val client = OkHttpClient()

            Log.d("API", "$url=============================")

            val request = Request.Builder().url(url)

            val response = client.newCall(request.build()).execute()

            var content = response.body()?.string()!!

            val xmlToJson = XmlToJson.Builder(content)
                .skipTag("channel/description")
                .skipTag("channel/link")
                .skipTag("channel/category")
                .skipTag("channel/description")
                .skipTag("channel/language")
                .skipTag("channel/copyright")
                .build()

            return xmlToJson.toJson()!!.getJSONObject("rss").getJSONObject("channel")
        }
    }

}