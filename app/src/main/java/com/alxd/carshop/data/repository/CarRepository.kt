package com.alxd.carshop.data.repository

import androidx.lifecycle.LiveData
import com.alxd.carshop.data.dao.CarsDao
import com.alxd.carshop.data.models.Car

class CarRepository(private val carsDao: CarsDao) {

    val getAllCars: LiveData<List<Car>> = carsDao.getAllData()

    suspend fun insertCar(car: Car){
        carsDao.insertData(car)
    }

    suspend fun updateCar(car:Car){
        carsDao.updateData(car)
    }

}