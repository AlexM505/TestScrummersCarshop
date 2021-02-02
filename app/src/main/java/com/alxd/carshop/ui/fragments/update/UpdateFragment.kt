package com.alxd.carshop.ui.fragments.update

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alxd.carshop.R
import com.alxd.carshop.data.models.Car
import com.alxd.carshop.data.viewmodels.CarViewModel
import com.alxd.carshop.databinding.FragmentUpdateBinding
import com.alxd.carshop.ui.fragments.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.etBatteryCapacity
import kotlinx.android.synthetic.main.fragment_update.etMaxAvailablePayload
import kotlinx.android.synthetic.main.fragment_update.etModel
import kotlinx.android.synthetic.main.fragment_update.etPrice
import kotlinx.android.synthetic.main.fragment_update.etSeats
import kotlinx.android.synthetic.main.fragment_update.etSpaceCapacity
import kotlinx.android.synthetic.main.fragment_update.spinnerCategory
import kotlinx.android.synthetic.main.fragment_update.spinnerStates
import kotlinx.android.synthetic.main.fragment_update.tvBatteryCapacity
import kotlinx.android.synthetic.main.fragment_update.tvDateReleased
import kotlinx.android.synthetic.main.fragment_update.tvMaxAvailablePayload
import kotlinx.android.synthetic.main.fragment_update.tvSpaceCapacity
import java.text.SimpleDateFormat
import java.util.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val mCarViewModel: CarViewModel by viewModels()

    var dateReleased:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args

        setHasOptionsMenu(true)

        DisableElectronicCar()
        spinnerCategoryListener()

        return binding.root
    }

    private fun DisableElectronicCar(){

        when(args.currentCar.category){
            "Electric" ->{
                binding.etModel.isEnabled = false
                binding.etPrice.isEnabled = false
                binding.etSeats.isEnabled = false
                binding.spinnerCategory.isEnabled = false
                binding.spinnerStates.isEnabled = false
                binding.etBatteryCapacity.visibility = View.VISIBLE
                binding.tvBatteryCapacity.visibility = View.VISIBLE
                binding.etBatteryCapacity.isEnabled = false
            }
            "Truck" ->{
                binding.etBatteryCapacity.visibility = View.INVISIBLE
                binding.tvBatteryCapacity.visibility = View.INVISIBLE
                binding.etMaxAvailablePayload.visibility = View.VISIBLE
                binding.tvMaxAvailablePayload.visibility = View.VISIBLE
                binding.etSpaceCapacity.visibility = View.INVISIBLE
                binding.tvSpaceCapacity.visibility = View.INVISIBLE
                binding.llDateReleased.setOnClickListener { showDatePickerDialog() }
            }
            "Commercial" ->{
                binding.etBatteryCapacity.visibility = View.INVISIBLE
                binding.tvBatteryCapacity.visibility = View.INVISIBLE
                binding.etMaxAvailablePayload.visibility = View.INVISIBLE
                binding.tvMaxAvailablePayload.visibility = View.INVISIBLE
                binding.etSpaceCapacity.visibility = View.VISIBLE
                binding.tvSpaceCapacity.visibility = View.VISIBLE
                binding.llDateReleased.setOnClickListener { showDatePickerDialog() }
            }
        }
    }

    private fun spinnerCategoryListener(){
        binding.spinnerCategory?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> {
                        tvBatteryCapacity.visibility = View.VISIBLE
                        etBatteryCapacity.visibility = View.VISIBLE
                        tvMaxAvailablePayload.visibility = View.INVISIBLE
                        etMaxAvailablePayload.visibility = View.INVISIBLE
                        tvSpaceCapacity.visibility = View.INVISIBLE
                        etSpaceCapacity.visibility = View.INVISIBLE
                    }
                    1 -> {
                        tvBatteryCapacity.visibility = View.INVISIBLE
                        etBatteryCapacity.visibility = View.INVISIBLE
                        tvMaxAvailablePayload.visibility = View.VISIBLE
                        etMaxAvailablePayload.visibility = View.VISIBLE
                        tvSpaceCapacity.visibility = View.INVISIBLE
                        etSpaceCapacity.visibility = View.INVISIBLE
                    }
                    2 -> {
                        tvBatteryCapacity.visibility = View.INVISIBLE
                        etBatteryCapacity.visibility = View.INVISIBLE
                        tvMaxAvailablePayload.visibility = View.INVISIBLE
                        etMaxAvailablePayload.visibility = View.INVISIBLE
                        tvSpaceCapacity.visibility = View.VISIBLE
                        etSpaceCapacity.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun showDatePickerDialog(){
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(requireActivity().supportFragmentManager, "")
    }

    @SuppressLint("SimpleDateFormat")
    private fun onDateSelected(day: Int, month: Int, year: Int){
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val c = Calendar.getInstance()
        c.set(year, month, day)
        dateReleased = c.timeInMillis.toString()

        tvDateReleased.setText(formatter.format(c.time))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_save -> {
                if(args.currentCar.category.equals("Electric"))
                    Toast.makeText(requireContext(), "This car is electronic, it cannot be modified",Toast.LENGTH_SHORT).show()
                else
                    updateItem()

            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val mModel = etModel.text.toString()
        val mPrice =  etPrice.text.toString()
        val mSeats = etSeats.text.toString()
        val mCategory = spinnerCategory.selectedItem.toString()
        val mState = spinnerStates.selectedItem.toString()
        val mBatteryCapacity = etBatteryCapacity.text.toString()
        val mMaxAvailablePayload = etMaxAvailablePayload.text.toString()
        val mSpaceCapacity = etSpaceCapacity.text.toString()

        val validation = verifyData(mModel, mPrice, mSeats)

        if(validation){

            if (dateReleased == "")
                dateReleased = args.currentCar.dateReleased

            val updateCar = Car(args.currentCar.id,
                mSeats.toInt(),
                mPrice.toDouble(),
                mModel,
                dateReleased,
                mCategory,
                mState,mBatteryCapacity,mMaxAvailablePayload,mSpaceCapacity)

            mCarViewModel.updateCar(updateCar)
            findNavController().navigate(R.id.action_updateFragment_to_mainCarsFragment)

        }else{
            Toast.makeText(requireContext(), "Please enter data in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    fun verifyData(model: String, price: String, seats: String): Boolean{
        return if(TextUtils.isEmpty(model) || TextUtils.isEmpty(price) || TextUtils.isEmpty(seats)){
            false
        }else !(model.isEmpty() || price.isEmpty() || seats.isEmpty())
    }

}