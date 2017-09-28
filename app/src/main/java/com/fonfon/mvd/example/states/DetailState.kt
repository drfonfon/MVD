package com.fonfon.mvd.example.states

import com.fonfon.mvd.example.Monster

interface DetailState {

  interface InputIntent : DetailState {
    data class Init(val monster: Monster) : InputIntent

    class ToggleButton : InputIntent
  }

  interface OutputState : DetailState {
    class Empty : OutputState

    data class Data(val monster: Monster) : OutputState
  }

}