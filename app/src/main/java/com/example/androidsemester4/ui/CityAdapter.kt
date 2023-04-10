package com.example.androidsemester4.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsemester4.databinding.ItemCityBinding
import com.example.androidsemester4.ui.Model.City
import com.bumptech.glide.RequestManager
import com.example.androidsemester4.ui.Model.CityItem

class CityAdapter(
    private val list:List<City>,
    private val glide:RequestManager,
    private val action:(City)->Unit
): RecyclerView.Adapter<CityItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItem =
        CityItem(
            binding = ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            action = action
        )

    override fun onBindViewHolder(holder: CityItem, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
