package com.newsapp.models

import org.json.JSONObject
import java.util.Date

data class Post(
    var id: Long,
    var title: String,
    var thumb: String,
    var url: String,
    var dateTime: String,
    var desc: String,
    var source: String
) {
    companion object {
        fun fromJson(jsonObj: JSONObject): Post {
            return Post(
                id = System.currentTimeMillis(),
                title =  jsonObj.getString("title"),
                desc =  jsonObj.getString("description"),
                url =  jsonObj.getString("url"),
                thumb =  jsonObj.getString("urlToImage"),
                source = jsonObj.getJSONObject("source").getString("name"),
                dateTime = jsonObj.getString("publishedAt")
            )
        }
    }
}