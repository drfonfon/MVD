package com.fonfon.mvd.example.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fonfon.mvd.example.R
import com.fonfon.mvd.example.adapters.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewpager.adapter = PagerAdapter(supportFragmentManager)
    tabs.setupWithViewPager(viewpager)
  }
}
