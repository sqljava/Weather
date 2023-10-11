package com.example.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weather.Day
import com.example.weather.databinding.ItemDayBinding
import com.example.weather.databinding.ItemHourBinding

class DayAdapter(var list: MutableList<Day>,
    val myDay: DayAdapter.DayClick):RecyclerView.Adapter<DayAdapter.DayHolder>() {
    class DayHolder(val binding: ItemDayBinding):RecyclerView.ViewHolder(binding.root){
        val icon = binding.hourIcon
        val temp = binding.dayTemp
        val date = binding.date


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        return DayHolder(ItemDayBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        val day = list[position]
        holder.date.text = day.date
        holder.temp.text = day.avgTemp.toString()+"℃"
        holder.icon.load(day.icon)

        myDay.onClick(position)
    }

    interface DayClick{
        fun onClick(position: Int)
    }
}