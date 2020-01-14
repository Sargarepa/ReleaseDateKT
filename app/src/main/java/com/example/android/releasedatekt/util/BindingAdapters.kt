package com.example.android.releasedatekt.util

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.android.releasedatekt.domain.Genre

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter(value = ["genreIds", "genres"])
fun setGenres(textView: TextView, genreIds: List<Int>, genres: List<Genre>) {
    val genreStringList: MutableList<String> = mutableListOf()
    for (genreId in genreIds) {
        for (genre in genres) {
            if (genreId == genre.id) {
                genreStringList.add(genre.name)
                break
            }
        }
    }
    val genreString = TextUtils.join(", ", genreStringList)
    textView.text = genreString
}