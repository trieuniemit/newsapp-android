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
import org.jetbrains.anko.doAsync
import java.lang.Exception
import java.net.URL


class LargeArticleAdapter(context: Context, var articles: ArrayList<Post>): BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val post = articles[position]

        var view = convertView

        if(view == null) {
            view = inflater.inflate(R.layout.adapter_large_arcticle, parent, false)
        }

        // thumbnail
        if(post.thumb.isNotEmpty()) {
            val thumb = view!!.findViewById<ImageView>(R.id.Thumbnail)
            doAsync {
                try {
                    val img = URL(post.thumb).openStream()
                    var bitmap = BitmapFactory.decodeStream(img)
                    thumb.setImageBitmap(bitmap)
                }catch (e: Exception) {
                    Log.d("Large List", e.toString())
                }
            }
        }

        var title = view!!.findViewById<TextView>(R.id.PostTitle)
        title.text = post.title

        var dateTime = view.findViewById<TextView>(R.id.PostDateTime)
        dateTime.text = post.dateTime

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