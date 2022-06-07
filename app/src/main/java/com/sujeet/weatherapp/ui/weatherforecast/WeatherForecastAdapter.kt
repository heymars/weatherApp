package com.sujeet.weatherapp.ui.weatherforecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sujeet.weatherapp.databinding.LayoutWeatherForecastItemBinding
import com.sujeet.weatherapp.model.Hour

/**
 * Created by Sujeet on 01/06/22.
 */
class WeatherForecastAdapter (private val forecastList: List<Hour>, private val clickListener: OnWeatherDataClickListener): RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>(){
    inner class WeatherForecastViewHolder(val binding: LayoutWeatherForecastItemBinding) :
    RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        val binding =
            LayoutWeatherForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        val hourModel = forecastList[position]
        holder.binding.weatherCondition.text = hourModel.condition.text
        holder.binding.tvTempValue.text = hourModel.temp_c.toString() + "\u2103"
        holder.itemView.setOnClickListener {
            clickListener.onWeatherDataClick(hourModel)
        }
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    interface OnWeatherDataClickListener {
        fun onWeatherDataClick(hour: Hour)
    }
}
