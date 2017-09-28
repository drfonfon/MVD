package com.fonfon.mvd.example.ui

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.fonfon.mvd.example.Monster
import com.fonfon.mvd.example.R
import com.fonfon.mvd.example.delegates.DetailDelegate
import com.fonfon.mvd.example.lib.App
import com.fonfon.mvd.example.lib.fromUrl
import com.fonfon.mvd.example.states.DetailState
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

  private var dark: Int = Color.BLACK
  private var light: Int = Color.WHITE

  companion object {
    val monsterkey = "monster arg"

    fun getIntent(context: Context, monster: Monster)
        = Intent(context, DetailActivity::class.java).putExtra(monsterkey, monster)
  }

  private var state: DetailState by DetailDelegate { state ->
    when (state) {
      is DetailState.OutputState.Data -> {
        updateFab(state.monster.isScary)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)
    dark = ContextCompat.getColor(this, R.color.dark)
    light = ContextCompat.getColor(this, R.color.light)
    val monster = intent.getParcelableExtra<Monster>(monsterkey)

    toolbar.title = monster.name
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24px)
    toolbar.setNavigationOnClickListener { finish() }
    image.fromUrl(monster.image)
    text.text = monster.text

    //Todo this is just an example and this behavior should not be
    monster.isScary = App.scaryMonsters.containsKey(monster.name)

    updateFab(monster.isScary)

    fab.setOnClickListener {
      state = DetailState.InputIntent.ToggleButton()
    }

    state = DetailState.InputIntent.Init(monster)
  }

  fun updateFab(scary: Boolean) {
    if (scary) {
      fab.setImageResource(R.drawable.ic_afraid_face_white)
      fab.backgroundTintList = ColorStateList.valueOf(dark)
    } else {
      fab.setImageResource(R.drawable.ic_afraid_face)
      fab.backgroundTintList = ColorStateList.valueOf(light)
    }
  }
}
