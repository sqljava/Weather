package com.example.weather.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weather.Hour
import com.example.weather.R
import com.example.weather.adapters.HourAdapter
import com.example.weather.databinding.FragmentMainBinding
import org.json.JSONObject
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter : HourAdapter
    val url = "http://api.weatherapi.com/v1/forecast.json?key=6c4cc4b3e43340098a883250230810&q=Tashkent&days=5&aqi=no&alerts=no"
    val hourList = mutableListOf<Hour>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        adapter = HourAdapter(hourList)

        binding.recHours.adapter = adapter

        val requestQueue = Volley.newRequestQueue(requireContext())

        val request = JsonObjectRequest(url,
            object : Response.Listener<JSONObject>{
                @SuppressLint("SetTextI18n")
                override fun onResponse(response: JSONObject?) {
                    getFromAPI(response)

                    val current = response?.getJSONObject("current")
                    val curTemp = current?.getDouble("temp_c")
                    val condition = current?.getJSONObject("condition")
                    val icon = condition?.getString("icon")
                    val wind = current?.getDouble("wind_kph")
                    val conditionText = condition?.getString("text")
                    val forecast = response?.getJSONObject("forecast")
                    val days = forecast!!.getJSONArray("forecastday")

                    val thisDay = days.getJSONObject(0)

                    val hours = thisDay.getJSONArray("hour")

                    for (i in 0 until hours.length()){
                        val hour = hours.getJSONObject(i)
                        val time = hour.getString("time")
                        val temp = hour.getDouble("temp_c")
                        val condition = hour.getJSONObject("condition")
                        val text = condition.getString("text")
                        val icon = condition.getString("icon")
                        var o = ""
                        for (a in 11..15){
                            o+=time.toCharArray()[a]

                        }

                        val obj = Hour("https:"+icon, temp, text, o)

                        hourList.add(obj)

                        adapter.notifyDataSetChanged()


                    }






                    binding.currentTemp.text = curTemp?.roundToInt().toString()+"℃"
                    binding.mainIcon.load("http:"+icon)
                    binding.wind.text = binding.wind.text.toString()+wind.toString()+" km/h"
                    binding.currentCondition.text = conditionText

                }

            },
            object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    Log.d("MyTag", "onErrorResponse: $error")
                }

            })

        requestQueue.add(request)






        return binding.root
    }

    fun getFromAPI(response: JSONObject?){

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}