package com.newsapp.activities.main.category

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
import com.newsapp.R
import com.newsapp.activities.detail.DetailActivity
import com.newsapp.adapters.LargeArticleAdapter
import com.newsapp.models.Post
import kotlinx.serialization.json.Json
import org.jetbrains.anko.doAsync

class ViewPagerFragment(val position: Int): Fragment() {

    private val paths = arrayListOf(
        "agriculture.rss",
        "life-style.rss",
        "sports.rss",
        "environment.rss",
        "travel.rss",
        "politics-laws.rss",
        "society.rss"
    )

    val posts = ArrayList<Post>()
    lateinit var loadingProgressBar: ProgressBar
    lateinit var listAdapter: LargeArticleAdapter
    lateinit var listView: ListView
    private val viewModel = CategoryViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        loadingProgressBar = view.findViewById(R.id.LoadingProgressBar)
        listView = view.findViewById(R.id.HeadlineListView)
        setupList()
        return view
    }

    private fun setupList() {
        Log.d("ViewPagerFragment", "Setup list $position")

        listAdapter = LargeArticleAdapter(requireContext(), posts)
        posts.clear()

        listView.apply {
            adapter = listAdapter

            setOnItemClickListener { parent, view, position, id ->
                var intent = Intent(context, DetailActivity::class.java)
                var post = posts[position]
                intent.putExtra("post", Json.stringify(Post.serializer(), post))
                requireActivity().startActivity(intent)
            }
        }

        viewModel.articleCount.observe(this, Observer {
            if(it.count() > 0) {
                posts.clear()
                posts.addAll(it)
                loadingProgressBar.visibility = View.GONE
                activity?.runOnUiThread {
                    listAdapter.notifyDataSetChanged()
                }
            }
        })

        doAsync {
            viewModel.getArticles(paths[position])
        }
    }
}