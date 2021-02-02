package com.alxd.carshop.ui.fragments.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alxd.carshop.R
import com.alxd.carshop.data.models.Car
import com.alxd.carshop.data.viewmodels.CarViewModel
import com.alxd.carshop.ui.fragments.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {

    private val mCarViewModel: CarViewModel by viewModels()
    var dateReleased:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        spinnerCategoryListener(view)

        view.llDateReleased.setOnClickListener { showDatePickerDialog() }

        return view
    }

    private fun spinnerCategoryListener(v: View){
        v.spinnerCategory?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_add){
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
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

    private fun insertDataToDb() {
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

            val newCar = Car(0,
                mSeats.toInt(),
                mPrice.toDouble(),
                mModel,
                dateReleased,
                mCategory,
                mState,
                mBatteryCapacity, mMaxAvailablePayload, mSpaceCapacity)

            mCarViewModel.insertCar(newCar)
            findNavController().navigate(R.id.action_addFragment_to_mainCarsFragment)

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