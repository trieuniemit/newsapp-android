package com.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsapp.constants.API_URL
import com.newsapp.constants.RSS_URL
import com.newsapp.constants.TOP_HEADLINE
import com.newsapp.constants.WORLD_NEWS
import com.newsapp.models.Post
import com.newsapp.services.ApiService
import com.newsapp.services.RequestTypes
import org.jetbrains.anko.doAsync


class HomeViewModel : ViewModel() {

    private val _headlineArticles = MutableLiveData<ArrayList<Post>>().apply {
        value = ArrayList()
    }
    val text: LiveData<ArrayList<Post>> = _headlineArticles

    init {
        doAsync {
            getHeadlineArticles()
            //ApiService.readText(RSS_URL+ WORLD_NEWS)
        }
    }


    private fun getHeadlineArticles() {
        var headlineArticles = ArrayList<Post>()

        var params = HashMap<String, Any>()
        params["country"] = "us"

        val res  = ApiService(API_URL + TOP_HEADLINE, RequestTypes.GET, params).execute()
        var articleJSON = res.getJSONArray("articles")

        for (i in 0 until articleJSON.length() ) {
            val post = Post.fromJson(articleJSON.getJSONObject(i))
            headlineArticles.add(post)
        }
        _headlineArticles.postValue(headlineArticles)
    }

    private fun getWorldNews() {

    }

}