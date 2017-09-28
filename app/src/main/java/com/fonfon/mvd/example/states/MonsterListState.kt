package com.fonfon.mvd.example.states

import com.fonfon.mvd.example.Monster

interface MonsterListState {

  interface InputIntent : MonsterListState {
    data class Init(val scary: Boolean) : InputIntent

    class Resume : InputIntent

    class Update : InputIntent

    data class DataSelect(val monster: Monster) : InputIntent
  }

  interface OutputState : MonsterListState {
    class Load : OutputState

    class Error : OutputState

    data class DataList(val monsters: ArrayList<Monster>) : OutputState

    data class OpenDetail(val monster: Monster): OutputState
  }

}