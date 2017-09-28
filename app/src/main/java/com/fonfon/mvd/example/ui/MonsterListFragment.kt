package com.fonfon.mvd.example.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fonfon.mvd.example.R
import com.fonfon.mvd.example.adapters.MonstersAdapter
import com.fonfon.mvd.example.delegates.MonsterListDelegate
import com.fonfon.mvd.example.states.MonsterListState
import kotlinx.android.synthetic.main.fragment_monster_list.*

class MonsterListFragment : Fragment() {

  companion object {

    val isScaryKey = "isScaryKey"

    fun instance(scary: Boolean): MonsterListFragment {
      val f = MonsterListFragment()
      val b = Bundle()
      b.putBoolean(isScaryKey, scary)
      f.arguments = b
      return f
    }
  }

  private var state: MonsterListState by MonsterListDelegate { state ->
    when (state) {
      is MonsterListState.OutputState.Load -> {
        errorText.visibility = View.GONE
        progress.visibility = View.VISIBLE
        recycler.visibility = View.GONE
      }
      is MonsterListState.OutputState.DataList -> {
        (recycler.adapter as MonstersAdapter).items = state.monsters
        recycler.adapter?.notifyDataSetChanged()
        errorText.visibility = View.GONE
        progress.visibility = View.GONE
        recycler.visibility = View.VISIBLE
      }
      is MonsterListState.OutputState.Error -> {
        errorText.visibility = View.VISIBLE
        progress.visibility = View.GONE
        recycler.visibility = View.GONE
      }
      is MonsterListState.OutputState.OpenDetail -> {
        startActivity(DetailActivity.getIntent(activity, state.monster))
      }
    }
    swipeToRefresh.isRefreshing = false
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
      = inflater?.inflate(R.layout.fragment_monster_list, container, false)

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recycler.layoutManager = LinearLayoutManager(activity)
    recycler.adapter = MonstersAdapter { monster -> state = MonsterListState.InputIntent.DataSelect(monster) }
    swipeToRefresh.setOnRefreshListener { state = MonsterListState.InputIntent.Update() }
    state = MonsterListState.InputIntent.Init(arguments.getBoolean(isScaryKey))
  }

  override fun onResume() {
    super.onResume()
    state = MonsterListState.InputIntent.Resume()
  }
}