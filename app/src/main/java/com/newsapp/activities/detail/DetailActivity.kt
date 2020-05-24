package com.newsapp.activities.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.newsapp.R
import com.newsapp.helpers.HtmlImageGetter
import com.newsapp.models.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull.content
import java.lang.Exception


class DetailActivity : AppCompatActivity() {
    var post: Post? = null
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        val postJson = intent?.getStringExtra("post")
        post = Json.parse(Post.serializer(), postJson!!)

        this.title = post?.title
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        try {
            Picasso.with(applicationContext).load(post!!.thumb).into(ThumbView)
        } catch (e: Exception) {
            ThumbView.setImageResource(R.drawable.placeholder)
        }

        viewModel.content.observe(this, Observer {
            if(it.isEmpty()) {
                return@Observer
            }
            if(it == "error") {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle(R.string.error)
                dialog.setMessage(R.string.error_when_load_post)
                dialog.setCancelable(false)
                dialog.setNegativeButton(R.string.retry) { _, _ ->
                    viewModel.getPostContent(post!!)
                }
                dialog.show()
                return@Observer
            }

            progressBar.visibility = View.GONE
            // Log.d("HTML: ", it)
            HtmlTextView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY, HtmlImageGetter(applicationContext, HtmlTextView), null) as Spannable
            } else {
                Html.fromHtml(content, HtmlImageGetter(applicationContext, HtmlTextView), null) as Spannable
            }
        })

        TitleText.text = post?.title
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
