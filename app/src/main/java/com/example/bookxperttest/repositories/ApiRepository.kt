package com.example.bookxperttest.repositories

import androidx.lifecycle.LiveData
import com.example.bookxperttest.dbHelper.ApiObjectDao
import com.example.bookxperttest.models.ApiObject
import com.example.bookxperttest.service.ApiService

class ApiRepository(
    private val apiService: ApiService,
    private val dao: ApiObjectDao
) {
    val allObjects: LiveData<List<ApiObject>> = dao.getAll()

    suspend fun fetchAndStoreObjects() {
        val response = apiService.fetchObjects()
        if (response.isSuccessful) {
            response.body()?.let {
                dao.insertAll(it)
            }
        }
    }
}
