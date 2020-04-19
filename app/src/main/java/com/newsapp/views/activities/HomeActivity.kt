package com.newsapp.views.activities

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.UiThread
import com.newsapp.R
import com.newsapp.helpers.LargeArticleAdapter
import com.newsapp.models.Post
import com.newsapp.viewmodels.HomeViewModel
import org.jetbrains.anko.act
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class HomeActivity : AppCompatActivity() {

    private val homeViewModel = HomeViewModel.getInstace()

    private var headlineListView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initHeadlineListView()

        var list = ArrayList<String>()

        list.add("aaaaa")

        //headlineListView?.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)

        getHeadlineArticles()
    }

    private fun initHeadlineListView() {
        headlineListView = findViewById(R.id.HeadlineListView)
    }

    private fun getHeadlineArticles() {
        doAsync {
            homeViewModel.getHeadlineArticales()

            uiThread {
                headlineListView?.adapter = LargeArticleAdapter(
                    act,
                    homeViewModel.headlineArticles
                )
            }
        }
    }
}
