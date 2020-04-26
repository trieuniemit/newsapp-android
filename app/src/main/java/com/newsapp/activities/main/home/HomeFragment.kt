package com.newsapp.activities.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.newsapp.R
import com.newsapp.activities.detail.DetailActivity
import com.newsapp.adapters.LargeArticleAdapter
import com.newsapp.models.Post
import kotlinx.serialization.json.Json


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel


    private var headlineListView: ListView? = null
    private var loadingProgressBar: ProgressBar? = null

    private var listObserver = Observer<ArrayList<Post>> {
        var adapter = LargeArticleAdapter(activity?.applicationContext!!, it)
        headlineListView!!.adapter = adapter
        loadingProgressBar!!.visibility = View.INVISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        loadingProgressBar = root.findViewById(R.id.LoadingProgressBar)
        headlineListView = root.findViewById(R.id.HeadlineListView)

        homeViewModel.list.observe(viewLifecycleOwner, listObserver)

        headlineListView!!.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(context, DetailActivity::class.java)

            var post = homeViewModel.list.value!![position]
            intent.putExtra("post", Json.stringify(Post.serializer(), post))

            this.startActivity(intent)
        }

        return root
    }


    override fun onDestroyView() {
        Log.d("AAAAA", "Distroy=====")
        super.onDestroyView()
        homeViewModel.list.removeObserver(listObserver)
    }
}