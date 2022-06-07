package com.sujeet.weatherapp.ui.weatherdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sujeet.weatherapp.R
import com.sujeet.weatherapp.databinding.FragmentWeatherDetailBinding
import com.sujeet.weatherapp.databinding.FragmentWeatherForecastBinding
import com.sujeet.weatherapp.ui.MainActivity
import com.sujeet.weatherapp.ui.weatherforecast.WeatherForecastAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.include.ivBackBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        setUpObserver()
    }

    private fun setUpObserver() {
        (requireActivity() as MainActivity).viewModel.weatherForecastDataLiveData.observe(this, {
            it?.let {
                binding.include.tvToolbarTitle.text = it.location.name
            }
        })

        (requireActivity() as MainActivity).viewModel.weatherHourDataLiveData.observe(this, {
            it?.let {
                binding.tvCondition.text = it.condition.text
                binding.tvTempValue.text = it.temp_c.toString() + "\u2103"
                binding.tvFeelsLike.text = "Feels Like : " + it.feelslike_c.toString() + "\u2103"
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
            WeatherDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}