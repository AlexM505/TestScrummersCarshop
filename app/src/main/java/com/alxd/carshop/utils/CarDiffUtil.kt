package com.alxd.carshop.utils

import androidx.recyclerview.widget.DiffUtil
import com.alxd.carshop.data.models.Car

class CarDiffUtil(private val oldList: List<Car>,
                  private val newList: List<Car>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].model == newList[newItemPosition].model
                && oldList[oldItemPosition].category == newList[newItemPosition].category
                && oldList[oldItemPosition].state == newList[newItemPosition].state
                && oldList[oldItemPosition].numberOfSeats == newList[newItemPosition].numberOfSeats
                && oldList[oldItemPosition].price == newList[newItemPosition].price
                && oldList[oldItemPosition].dateReleased == newList[newItemPosition].dateReleased
    }
}