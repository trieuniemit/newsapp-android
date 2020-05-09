package com.newsapp.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.newsapp.models.Post
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import com.newsapp.R
import android.graphics.BitmapFactory
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.uiThread
import java.lang.Exception
import java.net.URL


class InlineArticleAdapter(val context: Context, var articles: ArrayList<Post>): BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val post = articles[position]

        var view = convertView

        if(view == null) {
            view = inflater.inflate(R.layout.adapter_inline_arcticle, parent, false)
        }

        // thumbnail
        if(post.thumb.isNotEmpty()) {
            val thumb = view!!.findViewById<ImageView>(R.id.Thumbnail)
            Picasso.with(context).load(post.thumb).into(thumb)
        }

        var title = view!!.findViewById<TextView>(R.id.PostTitle)
        title.text = post.title

        var description = view.findViewById<TextView>(R.id.PostDescription)
        description.text = post.description

        return view
    }

    override fun getItem(position: Int): Any {
        return articles[position]
    }

    override fun getItemId(position: Int): Long {
        return articles[position].id
    }

    override fun getCount(): Int {
        Log.d("LargeArticleAdapter", "count: " + articles.size.toString())
        return articles.size
    }


}