package com.fonfon.mvd.example.lib

import android.app.Application
import com.fonfon.mvd.example.Monster
import com.github.kittinunf.fuel.core.FuelManager

class App: Application() {

  companion object {
    val scaryMonsters: HashMap<String, Monster> = HashMap()
  }

  override fun onCreate() {
    super.onCreate()
    FuelManager.instance.basePath = "https://private-7ed79-lovecraftmonsters.apiary-mock.com"
  }
}