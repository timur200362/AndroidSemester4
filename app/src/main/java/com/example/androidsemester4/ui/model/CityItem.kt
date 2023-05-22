package com.example.androidsemester4.ui.model

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.androidsemester4.databinding.ItemCityBinding

class CityItem(
    private val binding: ItemCityBinding,
    private val glide: RequestManager,
    private val action: (City) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(cityList: City) {
        with(binding) {
            tvCityName.text = cityList.name

            glide
                .load(cityList.icon)
                .into(ivCityIcon)

            root.setOnClickListener {
                action(cityList)
            }
        }
    }
}
