package com.example.bookxperttest.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookxperttest.dbHelper.UserEntity
import com.example.bookxperttest.repositories.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun saveUserToDB(user: UserEntity) {
        viewModelScope.launch {
            repository.saveUser(user)
            _loginResult.postValue(true)
        }
    }
}
