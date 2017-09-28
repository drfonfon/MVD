package com.fonfon.mvd.example.adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fonfon.mvd.example.ui.MonsterListFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
  override fun getItem(p: Int) = MonsterListFragment.instance(p == 1)
  override fun getCount() = 2
  override fun getPageTitle(p: Int) = if (p == 0) "All monsters" else "Scarry monsters"
}