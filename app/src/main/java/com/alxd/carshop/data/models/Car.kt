package com.alxd.carshop.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cars")
@Parcelize
data class Car(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var numberOfSeats: Int,
    var price: Double,
    var model: String,
    var dateReleased: String,
    var category: String,
    var state: String,
    var batteryCapacity: String,
    var maxAvailablePayload: String,
    var spaceCapacity: String
): Parcelable