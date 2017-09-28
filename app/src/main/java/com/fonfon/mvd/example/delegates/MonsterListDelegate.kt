package com.fonfon.mvd.example.delegates

import com.fonfon.mvd.example.Monster
import com.fonfon.mvd.example.lib.App
import com.fonfon.mvd.example.states.MonsterListState
import com.fonfon.mvd.example.states.MonsterListState.OutputState
import com.fonfon.mvd.example.ui.MonsterListFragment
import com.github.kittinunf.fuel.httpGet
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KProperty

class MonsterListDelegate(private val render: (intent: MonsterListState) -> Unit) {

  private var scary: Boolean = false

  private var outState: MonsterListState.OutputState = OutputState.Load()
    set(value) = render(value)

  operator fun getValue(arg: MonsterListFragment, property: KProperty<*>): MonsterListState = outState

  operator fun setValue(arg: MonsterListFragment, property: KProperty<*>, value: MonsterListState) {
    when (value) {
      is MonsterListState.InputIntent.Init -> {
        scary = value.scary
        if(scary) getLocalMonsters()
        else getMonsters()
      }
      is MonsterListState.InputIntent.Resume -> {
        if(scary) getLocalMonsters()
        else render(outState)
      }
      is MonsterListState.InputIntent.Update -> {
        if(scary) getLocalMonsters()
        else getMonsters()
      }
      is MonsterListState.InputIntent.DataSelect -> {
        //TODO: expected navigator
        outState = OutputState.OpenDetail(value.monster)
      }
    }
  }

  private fun getLocalMonsters() {
    outState = OutputState.DataList(ArrayList(App.scaryMonsters.values))
  }

  private fun getMonsters() {
    outState = OutputState.Load()
    "/monsters".httpGet().responseObject(Monster.Deserializer()) { _, _, result ->

      //random test error
      if(Random().nextInt(100) > 80) {
        outState = OutputState.Error()
        return@responseObject
      }

      //result
      result.component1()?.let {
        outState = OutputState.DataList(it)
        return@responseObject
      }

      //real error
      result.component2()?.let {
        outState = OutputState.Error()
      }
    }
  }

}