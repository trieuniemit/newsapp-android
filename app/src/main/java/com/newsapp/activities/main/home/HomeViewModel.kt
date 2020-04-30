package com.newsapp.activities.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsapp.constants.RSS_URL
import com.newsapp.constants.WORLD_NEWS
import com.newsapp.models.Post
import com.newsapp.helpers.RssReader
import org.jetbrains.anko.doAsync
import kotlin.reflect.KProperty


class HomeViewModel : ViewModel() {
    val headPosts = ArrayList<Post>()

    private val _articleCount = MutableLiveData<Int>()
    val articleCount: LiveData<Int> = _articleCount

    fun getHeadlineArticles() {
        val res = RssReader.readRss(RSS_URL+ WORLD_NEWS)

        val articleJSON = res!!.getJSONArray("item")

        headPosts.clear()
        for (i in 0 until articleJSON.length() ) {
            val post = Post.fromJson(articleJSON.getJSONObject(i))
            headPosts.add(post)
        }
        Log.d("Load headline", "Count ${headPosts.count()}")
        _articleCount.postValue(headPosts.count())
    }

}