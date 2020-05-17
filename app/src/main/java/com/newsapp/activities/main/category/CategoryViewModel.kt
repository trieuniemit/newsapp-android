package com.newsapp.activities.main.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsapp.constants.RSS_URL
import com.newsapp.constants.WORLD_NEWS
import com.newsapp.helpers.RssReader
import com.newsapp.models.Post

class CategoryViewModel : ViewModel() {

    private val _articleCount = MutableLiveData<ArrayList<Post>>()
    val articleCount: LiveData<ArrayList<Post>> = _articleCount

    fun getArticles(path: String) {
        val res = RssReader.readRss(RSS_URL + path)
        val posts = ArrayList<Post>()

        val articleJSON = res!!.getJSONArray("item")
        for (i in 0 until articleJSON.length() ) {
            val post = Post.fromJson(articleJSON.getJSONObject(i))
            posts.add(post)
        }
        Log.d("Load post", "Count ${posts.count()}")
        _articleCount.postValue(posts)
    }
}