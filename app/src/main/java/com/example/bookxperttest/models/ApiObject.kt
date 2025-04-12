package com.example.bookxperttest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.bookxperttest.dbHelper.Converters

@Entity(tableName = "api_objects")
@TypeConverters(Converters::class)
data class ApiObject(
    @PrimaryKey val id: String,
    val name: String?,
    val data: Map<String, Any>?
)

data class ApiObjectData(
    val color: String? = null,
    val capacity: String? = null,
    @ColumnInfo(name = "capacity_GB") val capacityGB: Int? = null,
    val price: Double? = null,
    val generation: String? = null,
    @ColumnInfo(name = "cpu_model") val cpuModel: String? = null,
    @ColumnInfo(name = "hard_disk_size") val hardDiskSize: String? = null,
    @ColumnInfo(name = "strap_colour") val strapColour: String? = null,
    @ColumnInfo(name = "case_size") val caseSize: String? = null,
    val description: String? = null,
    @ColumnInfo(name = "screen_size") val screenSize: Double? = null
)