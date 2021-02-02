package com.alxd.carshop.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.alxd.carshop.data.CarsDatabase
import com.alxd.carshop.data.models.Car
import com.alxd.carshop.data.repository.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel(application: Application) : AndroidViewModel(application) {

    private val carsDao = CarsDatabase.getDatabase(application).carsDao()
    private val repo: CarRepository

    val getAllCars: LiveData<List<Car>>

    init {
        repo = CarRepository(carsDao)
        getAllCars = repo.getAllCars
    }

    fun insertCar(car:Car){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertCar(car)
        }
    }

    fun updateCar(car: Car){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateCar(car)
        }
    }

}