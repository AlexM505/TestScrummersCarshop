package com.alxd.carshop.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alxd.carshop.data.models.Car
import com.alxd.carshop.databinding.CarLayoutBinding
import com.alxd.carshop.utils.CarDiffUtil

class CarsAdapter : RecyclerView.Adapter<CarsAdapter.MyViewHolder>() {

    var dataListCar = emptyList<Car>()

    class MyViewHolder(private val binding: CarLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(car: Car){
            binding.car = car
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CarLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataListCar[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return dataListCar.size
    }

    fun setCarsData(carsData: List<Car>){
        val carDiffUtil = CarDiffUtil(dataListCar, carsData)
        val carDiffUtilResult = DiffUtil.calculateDiff(carDiffUtil)
        this.dataListCar = carsData
        carDiffUtilResult.dispatchUpdatesTo(this)
    }

}