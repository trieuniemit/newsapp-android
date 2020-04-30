package com.newsapp.activities.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.newsapp.R
import com.newsapp.activities.detail.DetailActivity
import com.newsapp.adapters.LargeArticleAdapter
import com.newsapp.models.Post
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.serialization.json.Json
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    lateinit var listAdapter: LargeArticleAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this, HomeViewModelFactory()).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = LargeArticleAdapter(context!!, homeViewModel.headPosts)

        HeadlineListView.apply {
            adapter = listAdapter

            setOnItemClickListener { parent, view, position, id ->
                var intent = Intent(context, DetailActivity::class.java)
                var post = homeViewModel.headPosts[position]
                intent.putExtra("post", Json.stringify(Post.serializer(), post))
                activity!!.startActivity(intent)
            }
        }

        homeViewModel.articleCount.observe(this, Observer {
            if(it > 0) {
                LoadingProgressBar.visibility = View.GONE
                activity?.runOnUiThread {
                    listAdapter.notifyDataSetChanged()
                }
            }
        })

        doAsync {
            homeViewModel.getHeadlineArticles()
        }
    }
}