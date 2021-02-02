package com.alxd.carshop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alxd.carshop.data.dao.CarsDao
import com.alxd.carshop.data.models.Car

@Database(entities = [Car::class], version = 1, exportSchema = false)
abstract  class CarsDatabase : RoomDatabase(){

    abstract  fun carsDao(): CarsDao

    companion object{
        @Volatile
        private var INSTANCE: CarsDatabase?= null

        fun getDatabase(context: Context): CarsDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarsDatabase::class.java,
                    "cars_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}