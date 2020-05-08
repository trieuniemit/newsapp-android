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
    private val _postContent = MutableLiveData<String>()
    val content: LiveData<String> = _postContent

    fun getPostContent(post: Post) {
        doAsync {
            try {
                val siteContent = Jsoup.connect(post.url).get()
                val postContent = siteContent.getElementsByClass("vnnews-text-post")
                postContent.select(".inline-image").remove()
                _postContent.postValue(postContent.html().replace("<p>&nbsp;</p>", ""))
            } catch (e: Exception) {
                Log.d("Detail Error", e.toString())
                _postContent.postValue("error")
            }

        }
    }

}