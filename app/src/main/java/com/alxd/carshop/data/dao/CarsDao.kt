package com.alxd.carshop.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alxd.carshop.data.models.Car

@Dao
interface CarsDao {

    @Query("SELECT * FROM cars ORDER BY id ASC")
    fun getAllData(): LiveData<List<Car>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(car: Car)

    @Update
    suspend fun updateData(car: Car)

}