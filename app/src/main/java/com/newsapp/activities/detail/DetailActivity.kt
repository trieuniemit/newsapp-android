package com.newsapp.activities.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.newsapp.R
import com.newsapp.helpers.HtmlImageGetter
import com.newsapp.models.Post
import com.squareup.picasso.Picasso
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull.content


class DetailActivity : AppCompatActivity() {
    var post: Post? = null
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val postJson = intent?.getStringExtra("post")
        post = Json.parse(Post.serializer(), postJson!!)

        this.title = post?.title
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        var thumb = findViewById<ImageView>(R.id.ThumbView)
        Picasso.with(applicationContext).load(post!!.thumb).into(thumb)

        viewModel.content.observe(this, Observer {
            var htmlView = findViewById<TextView>(R.id.HtmlTextView)

            htmlView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY, HtmlImageGetter(applicationContext, htmlView), null) as Spannable
            } else {
                Html.fromHtml(content, HtmlImageGetter(applicationContext, htmlView), null) as Spannable
            }
        })

        viewModel.getPostContent(post!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
