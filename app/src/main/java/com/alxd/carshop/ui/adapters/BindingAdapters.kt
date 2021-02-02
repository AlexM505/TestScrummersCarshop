package com.alxd.carshop.ui.adapters

import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.alxd.carshop.R
import com.alxd.carshop.data.models.Car
import com.alxd.carshop.ui.fragments.main.MainCarsFragment
import com.alxd.carshop.ui.fragments.main.MainCarsFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_add.*
import java.text.SimpleDateFormat
import java.util.*

class BindingAdapters {

    companion object{
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean){
            view.setOnClickListener {
                if(navigate)
                    view.findNavController().navigate(R.id.action_mainCarsFragment_to_addFragment)
            }
        }

        @BindingAdapter("android:sendDataToUpdate")
        @JvmStatic
        fun sendDataToUpdate(view: ConstraintLayout, currentCar: Car){
            view.setOnClickListener {

                val action = MainCarsFragmentDirections.actionMainCarsFragmentToUpdateFragment(currentCar)
                view.findNavController().navigate(action)
            }
        }

        @BindingAdapter("android:setConvertDoubleToText")
        @JvmStatic
        fun setConvertDoubleToText(view: TextView, price: Double){
            view.setText("$ $price")
        }

        @BindingAdapter("android:setPriceEditTextt")
        @JvmStatic
        fun setPriceEditTextt(view: EditText, price: Double){
            view.setText("$ $price")
        }

        @BindingAdapter("android:setConvertIntToText")
        @JvmStatic
        fun setConvertIntToText(view: EditText, value: Int){
            view.setText("$value")
        }

        @BindingAdapter("android:setConvertDouble")
        @JvmStatic
        fun setConvertDouble(view: EditText, value: Double){
            view.setText("$value")
        }

        @BindingAdapter("android:setFormatDateToText")
        @JvmStatic
        fun setFormatDateToText(view: TextView, dateReleased: String){
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val c = Calendar.getInstance()
            c.timeInMillis = dateReleased.toLong()

            view.setText(formatter.format(c.time))
        }

        @BindingAdapter("android:parseStateToInt")
        @JvmStatic
        fun parseStateToInt(view: Spinner, state:String ){
            when(state){
                "New" -> {view.setSelection(0)}
                "Used" -> {view.setSelection(1)}
            }
        }

        @BindingAdapter("android:parseCategoryToInt")
        @JvmStatic
        fun parseCategoryToInt(view: Spinner, category:String ){
            when(category){
                "Electric" -> {view.setSelection(0)}
                "Truck" -> {view.setSelection(1)}
                "Commercial" -> {view.setSelection(2)}
            }
        }

    }

}