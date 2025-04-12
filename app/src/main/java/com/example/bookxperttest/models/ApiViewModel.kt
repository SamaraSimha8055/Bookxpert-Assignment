package com.example.bookxperttest.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bookxperttest.dbHelper.AppDatabase
import com.example.bookxperttest.service.ApiClient
import com.example.bookxperttest.utils.NotificationPreferenceManager
import com.example.bookxperttest.utils.NotificationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ApiViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    val apiObjects: LiveData<List<ApiObject>> = db.apiObjectDao().getAll()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun fetchDataIfNeeded() {
        viewModelScope.launch(Dispatchers.IO) {
            val existingData = db.apiObjectDao().getAllOnce()
            if (existingData.isNullOrEmpty()) {
                _loading.postValue(true)
                try {
                    val response = ApiClient.apiService.fetchObjects()
                    if (response.isSuccessful && response.body() != null) {
                        db.apiObjectDao().insertAll(response.body()!!)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _loading.postValue(false)
                }
            }
        }
    }

    fun updateItem(apiObject: ApiObject) {
        viewModelScope.launch(Dispatchers.IO) {
            db.apiObjectDao().update(apiObject)
        }
    }

    fun deleteItem(apiObject: ApiObject) {
        viewModelScope.launch(Dispatchers.IO) {
            db.apiObjectDao().delete(apiObject)

            val context = getApplication<Application>().applicationContext
            val enabled = NotificationPreferenceManager.getNotificationsEnabled(context).first()
            if (enabled) {
                NotificationUtils.showNotification(
                    context,
                    "Item Deleted",
                    "${apiObject.name ?: "Item"} has been deleted"
                )
            }
        }
    }
}