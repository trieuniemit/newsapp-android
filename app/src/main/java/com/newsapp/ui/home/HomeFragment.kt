package com.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.newsapp.R
import com.newsapp.helpers.LargeArticleAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var headlineListView: ListView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        headlineListView = root.findViewById(R.id.HeadlineListView)

        homeViewModel.text.observe(this, Observer {
            var adapter = LargeArticleAdapter(activity?.applicationContext!!, it)
            headlineListView!!.adapter = adapter
        })


        return root
    }


}