package com.newsapp.activities.main.search

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
import com.newsapp.activities.main.MainActivity
import com.newsapp.adapters.InlineArticleAdapter
import com.newsapp.models.Post
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.serialization.json.Json
import org.jetbrains.anko.doAsync

class SearchFragment : Fragment() {

    lateinit var listAdapter: InlineArticleAdapter
    lateinit var viewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = (activity as MainActivity).searchViewModel
        val homeViewModel = (activity as MainActivity).homeViewModel
        viewModel.posts.addAll(homeViewModel.headPosts)

        listAdapter = InlineArticleAdapter(requireContext(), viewModel.posts)

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listPosts.adapter = listAdapter
        listPosts.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(context, DetailActivity::class.java)
            var post = viewModel.posts[position]
            intent.putExtra("post", Json.stringify(Post.serializer(), post))
            activity!!.startActivity(intent)
        }

        searchBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            notFoundText.visibility = View.GONE

            doAsync {
                viewModel.search(keywordField.text.toString())
            }
        }

        viewModel.postsLoaded.observe(this, Observer {
            listAdapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE

            if(!it) {
                notFoundText.visibility = View.VISIBLE
            }
        })
    }
}