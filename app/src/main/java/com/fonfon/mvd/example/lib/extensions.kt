package com.fonfon.mvd.example.lib

import android.support.v7.widget.AppCompatImageView
import com.squareup.picasso.Picasso

fun AppCompatImageView.fromUrl(url: String) {
  Picasso
      .with(context)
      .load(url)
      .into(this)
}