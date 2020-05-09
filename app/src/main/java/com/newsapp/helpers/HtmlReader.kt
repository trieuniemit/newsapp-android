package com.newsapp.helpers

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class HtmlReader(var url: String) {

    companion object {

        fun read(url: String): Document {
            val client = OkHttpClient()

            Log.d("API", "$url =============================")

            val request = Request.Builder().url(url)

            val response = client.newCall(request.build()).execute()

            val content = response.body()?.string()!!

            return Jsoup.parse(content)
        }
    }

}