package com.fonfon.mvd.example.delegates

import com.fonfon.mvd.example.Monster
import com.fonfon.mvd.example.lib.App
import com.fonfon.mvd.example.states.DetailState
import com.fonfon.mvd.example.ui.DetailActivity
import kotlin.reflect.KProperty

class DetailDelegate(private val render: (intent: DetailState) -> Unit) {

  private lateinit var monster: Monster
  private var outState: DetailState.OutputState = DetailState.OutputState.Empty()
    set(value) = render(value)

  operator fun getValue(arg: DetailActivity, property: KProperty<*>): DetailState = outState

  operator fun setValue(arg: DetailActivity, property: KProperty<*>, value: DetailState) {
    when (value) {
      is DetailState.InputIntent.Init -> {
        monster = value.monster
      }
      is DetailState.InputIntent.ToggleButton -> {
        if(monster.isScary) {
          monster.isScary = false
          App.scaryMonsters.remove(monster.name)
        } else {
          monster.isScary = true
          //TODO we all know that there should be saving in the database
          App.scaryMonsters.put(monster.name, monster)
        }
        outState = DetailState.OutputState.Data(monster)
      }
    }
  }

}