package com.sujeet.weatherapp.ui.weatherforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.sujeet.weatherapp.R
import com.sujeet.weatherapp.databinding.FragmentWeatherForecastBinding
import com.sujeet.weatherapp.model.Hour
import com.sujeet.weatherapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class WeatherForecastFragment : Fragment() , WeatherForecastAdapter.OnWeatherDataClickListener{
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentWeatherForecastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        binding.include.ivBackBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setUpObserver() {
        (requireActivity() as MainActivity).viewModel.weatherForecastDataLiveData.observe(this, {
            it?.let {
                binding.include.tvToolbarTitle.text = it.location.name
                val adapter = WeatherForecastAdapter(it.forecast.forecastday[0].hour, this)
                val llm = LinearLayoutManager(requireContext())
                llm.orientation = LinearLayoutManager.VERTICAL
                binding.weatherRecyclerView.layoutManager = llm
                binding.weatherRecyclerView.adapter = adapter
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherForecastFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onWeatherDataClick(hour: Hour) {
        (requireActivity() as MainActivity).viewModel.weatherHourDataLiveData.value = hour
        Navigation.findNavController(requireView()).navigate(R.id.weatherDetailFragment)
    }
}