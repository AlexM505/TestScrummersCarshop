package com.alxd.carshop.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alxd.carshop.R
import com.alxd.carshop.data.viewmodels.CarViewModel
import com.alxd.carshop.databinding.FragmentMainCarsBinding
import com.alxd.carshop.ui.adapters.CarsAdapter
import com.alxd.carshop.utils.hideKeyboard
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator

class MainCarsFragment : Fragment() {

    private var _binding: FragmentMainCarsBinding? = null
    private val binding get() = _binding!!

    private val carViewModel: CarViewModel by viewModels()
    private val carsAdapter: CarsAdapter by lazy { CarsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainCarsBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this

        dataRecyclerview()

        carViewModel.getAllCars.observe(viewLifecycleOwner, {
            carsAdapter.setCarsData(it)
        })

        hideKeyboard(requireActivity())

        return binding.root
    }

    private fun dataRecyclerview() {
        val rvCars = binding.rvCars
        rvCars.adapter = carsAdapter
        rvCars.layoutManager = LinearLayoutManager(requireContext())
        rvCars.itemAnimator = SlideInDownAnimator().apply {
            addDuration = 350
        }
    }

}