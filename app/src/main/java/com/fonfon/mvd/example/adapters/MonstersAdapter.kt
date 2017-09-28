package com.fonfon.mvd.example.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fonfon.mvd.example.Monster
import com.fonfon.mvd.example.R
import com.fonfon.mvd.example.lib.fromUrl
import kotlinx.android.synthetic.main.item_monster.view.*

class MonstersAdapter(val listener: (monster: Monster) -> Unit) : RecyclerView.Adapter<MonstersAdapter.Holder>() {

  var items: ArrayList<Monster> = ArrayList()

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: Holder?, position: Int) {
    holder?.bind(items[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = Holder(
      LayoutInflater.from(parent?.context).inflate(R.layout.item_monster, parent, false)
  )

  inner class Holder(view: View) : RecyclerView.ViewHolder(view) {

    init {
      itemView.setOnClickListener { listener(items[adapterPosition]) }
    }

    fun bind(monster: Monster) {
      itemView.image.fromUrl(monster.image)
      itemView.text.text = monster.name
    }
  }
}
