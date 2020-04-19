package com.newsapp.viewmodels

import android.util.Log
import com.newsapp.constants.API_URL
import com.newsapp.constants.TOP_HEADLINE
import com.newsapp.models.Post
import com.newsapp.services.ApiService
import com.newsapp.services.RequestTypes
import org.json.JSONObject

class HomeViewModel private constructor(){
    companion object {
        var instance: HomeViewModel? = null

        fun getInstace(): HomeViewModel {
            if(instance == null) {
                instance = HomeViewModel()
            }

            return instance as HomeViewModel
        }
    }

    var headlineArticles = ArrayList<Post>()


    fun getHeadlineArticales() {

        var params = HashMap<String, Any>()
        params["country"] = "us"

        val res  = ApiService(API_URL + TOP_HEADLINE, RequestTypes.GET, params).execute()
        var articleJsons = res.getJSONArray("articles")

        for (i in 0 until articleJsons.length() ) {
            val post = Post.fromJson(articleJsons.getJSONObject(i))
            headlineArticles.add(post)
        }
    }


}