package com.newsapp.activities.main.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsapp.constants.RSS_URL
import com.newsapp.constants.SEARCH
import com.newsapp.constants.WORLD_NEWS
import com.newsapp.helpers.HtmlReader
import com.newsapp.helpers.RssReader
import com.newsapp.models.Post
import java.util.*
import kotlin.collections.ArrayList

class SearchViewModel : ViewModel() {

    val posts = ArrayList<Post>() 

    private val _postsLoaded = MutableLiveData<Boolean>()
    val postsLoaded: LiveData<Boolean> = _postsLoaded

    fun search(keyword: String) {

        val res = HtmlReader.read(SEARCH.replace("{keyword}", keyword))

        val articleHtml = res.select(".vnnews-list-news ul li")

        posts.clear()

        articleHtml.forEach { item ->
            val title = item.select(".vnnews-tt-list-news").text()
            val url = item.select("a").attr("href")
            val thumb = item.select("img").attr("src")
            val desc = item.select("p").text()

            posts.add(Post(
                id = System.currentTimeMillis(),
                thumb = thumb,
                title = title,
                url = url,
                description = desc,
                dateTime = ""
            ))
        }

        Log.d("Total found", "Count ${posts.count()}")
        if(posts.count() >= 2) {
            _postsLoaded.postValue(true)
        } else {
            posts.clear()
            _postsLoaded.postValue(false)
        }

    }
}