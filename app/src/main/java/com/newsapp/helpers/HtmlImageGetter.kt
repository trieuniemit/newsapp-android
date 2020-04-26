package com.newsapp.helpers

import android.R
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.util.Log
import android.widget.TextView
import org.jetbrains.anko.doAsync
import java.net.URL


class HtmlImageGetter(var context: Context, var textView: TextView): Html.ImageGetter {

    override fun getDrawable(source: String?): Drawable {
        return URLDrawable()

//        return try {
//            var drawable = URLDrawable()
//
//            doAsync {
//                val bitmap = BitmapFactory.decodeStream(URL(source).openStream())
//                val d: Drawable = BitmapDrawable(bitmap)
//                d.setBounds(
//                    0,
//                    0,
//                    d.intrinsicWidth,
//                    d.intrinsicHeight
//                )
//
//                Log.d("=======", "Finish")
//                drawable.drawable = d
//            }
//
//            drawable
//        } catch (e: Exception) {
//            Log.d("Image Getter Error", e.toString())
//            val drawable: Drawable = context.resources.getDrawable(R.drawable.ic_dialog_alert)
//            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
//            drawable
//        }
    }
}


class URLDrawable : BitmapDrawable() {
    // the drawable that you need to set, you could set the initial drawing
    // with the loading image if you need to
    var drawable: Drawable? = null

    override  fun draw(canvas: Canvas) {
        // override the draw to facilitate refresh function later
        if (drawable != null) {
            drawable!!.draw(canvas)
        }
    }
}