package com.newsapp.models

import kotlinx.serialization.Serializable
import org.json.JSONObject
import org.jsoup.Jsoup

@Serializable
class Post (
    var id: Long,
    var title: String,
    var thumb: String,
    var url: String,
    var dateTime: String,
    var description: String
) {
    companion object {
        fun fromJson(jsonObj: JSONObject): Post {
            var descHtml = Jsoup.parse(jsonObj.getString("description"))
            var thumb = descHtml.getElementsByTag("img")

            return Post(
                id = System.currentTimeMillis(),
                title =  jsonObj.getString("title"),
                description = descHtml.text(),
                url =  jsonObj.getString("link"),
                thumb =  thumb.attr("src"),
                dateTime = jsonObj.getString("pubDate")
            )
        }
    }
}