package com.sujeet.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.sujeet.weatherapp.R
import com.sujeet.weatherapp.databinding.FragmentCityLookUpBinding
import com.sujeet.weatherapp.utils.ResourceState
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class CityLookUpFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setUpObserver()
    }

    private var _binding: FragmentCityLookUpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityLookUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLookUp.setOnClickListener {
            if (!binding.etCityName.text.isNullOrEmpty()) {
                (requireActivity() as MainActivity).viewModel.getWeatherDataByCityName(binding.etCityName.text.toString().trim())
            } else {
                Toast.makeText(requireContext(), requireContext().getString(R.string.please_enter_city_name), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpObserver() {
        (requireActivity() as MainActivity).viewModel.weatherDataLiveData.observe(this, {
            it?.let {
                when (it.status) {
                    ResourceState.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    ResourceState.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        Navigation.findNavController(requireView())
                            .navigate(R.id.weatherForecastFragment)
                    }
                    ResourceState.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), requireContext().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityLookUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}