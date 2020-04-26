package com.newsapp.activities.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsapp.models.Post
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import java.lang.Exception


class DetailViewModel: ViewModel() {
    private val _postContent = MutableLiveData<String>().apply {
        value = ""
    }
    val content: LiveData<String> = _postContent

    fun getPostContent(post: Post) {
        doAsync {
            try {
                var siteContent = Jsoup.connect(post.url).get()
                var postContent = siteContent.getElementsByClass("vnnews-text-post")
                _postContent.postValue(postContent.html())
            } catch (e: Exception) {
                Log.d("Detail Error", e.toString())
            }

        }
    }

}