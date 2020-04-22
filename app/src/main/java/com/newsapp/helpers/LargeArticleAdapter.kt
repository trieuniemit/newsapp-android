package com.newsapp.helpers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.newsapp.models.Post
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import com.newsapp.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import java.net.URL


class LargeArticleAdapter(var context: Context, var articles: ArrayList<Post>): BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val post = articles[position]

        var view = convertView

        if(view == null) {
            view = inflater.inflate(R.layout.item_large_arcticle, parent, false)
        }

        // thumbnail
        val thumb = view!!.findViewById<ImageView>(R.id.Thumbnail)


//        doAsync {
//            val url = URL(post.thumb)
//            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//            thumb.setImageBitmap(bmp)
//        }

        Picasso.with(context).load(post.thumb).into(thumb)


//        val datetime = view.findViewById<TextView>(R.id.DateTime)
//        datetime.text = post.dateTime

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