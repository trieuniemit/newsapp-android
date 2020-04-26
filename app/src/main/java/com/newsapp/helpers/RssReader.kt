package com.newsapp.helpers

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import fr.arnaudguyon.xmltojsonlib.XmlToJson


class RssReader(var url: String) {

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