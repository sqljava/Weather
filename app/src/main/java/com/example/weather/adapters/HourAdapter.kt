package com.example.weather.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weather.Hour
import com.example.weather.databinding.ItemHourBinding
import kotlin.math.roundToInt

class HourAdapter(var list: MutableList<Hour>):RecyclerView.Adapter<HourAdapter.HourHolder>() {
    class HourHolder(var binding: ItemHourBinding):RecyclerView.ViewHolder(binding.root){
        var time = binding.hour
        var icon = binding.hourIcon
        var temp = binding.hourTemp

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder {
        return HourHolder(ItemHourBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HourHolder, position: Int) {
        var item = list[position]

        holder.time.text = item.time
        holder.temp.text = item.temp_c.roundToInt().toString()+"℃"
        holder.icon.load(item.icon)

    }
}