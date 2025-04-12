package com.example.bookxperttest.dbHelper

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bookxperttest.models.ApiObject

@Dao
interface ApiObjectDao {
    @Query("SELECT * FROM api_objects")
    fun getAll(): LiveData<List<ApiObject>>

    @Query("SELECT * FROM api_objects")
    suspend fun getAllOnce(): List<ApiObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objects: List<ApiObject>)

    @Update
    suspend fun update(apiObject: ApiObject)

    @Delete
    suspend fun delete(apiObject: ApiObject)
}
