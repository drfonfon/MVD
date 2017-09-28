package com.fonfon.mvd.example

import android.os.Parcel
import android.os.Parcelable
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


data class Monster(val name: String, val image: String, val text: String, var isScary: Boolean = false) : Parcelable {

  constructor(parcel: Parcel) : this(
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readByte() != 0.toByte())

  class Deserializer : ResponseDeserializable<ArrayList<Monster>> {
    private val type = object : TypeToken<ArrayList<Monster>>() {}.type
    override fun deserialize(content: String): ArrayList<Monster>? = Gson().fromJson(content, type)
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(name)
    parcel.writeString(image)
    parcel.writeString(text)
    parcel.writeByte(if (isScary) 1 else 0)
  }

  override fun describeContents() = 0

  companion object CREATOR : Parcelable.Creator<Monster> {
    override fun createFromParcel(parcel: Parcel) = Monster(parcel)
    override fun newArray(size: Int): Array<Monster?> = arrayOfNulls(size)
  }
}