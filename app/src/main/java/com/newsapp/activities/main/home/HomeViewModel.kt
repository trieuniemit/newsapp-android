package com.newsapp.activities.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsapp.constants.RSS_URL
import com.newsapp.constants.WORLD_NEWS
import com.newsapp.models.Post
import com.newsapp.helpers.RssReader
import org.jetbrains.anko.doAsync


class HomeViewModel : ViewModel() {

    private val _headlineArticles = MutableLiveData<ArrayList<Post>>().apply {
        value = ArrayList()
    }
    val list: LiveData<ArrayList<Post>> = _headlineArticles

    init {
        doAsync {
            getHeadlineArticles()
        }
    }

    private fun getHeadlineArticles() {
        var headlineArticles = ArrayList<Post>()

        var res = RssReader.readRss(RSS_URL+ WORLD_NEWS)

        var articleJSON = res!!.getJSONArray("item")

        for (i in 0 until articleJSON.length() ) {
            val post = Post.fromJson(articleJSON.getJSONObject(i))
            headlineArticles.add(post)
        }

        _headlineArticles.postValue(headlineArticles)
    }

}