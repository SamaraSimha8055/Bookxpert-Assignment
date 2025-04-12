package com.example.bookxperttest.repositories

import com.example.bookxperttest.dbHelper.UserDao
import com.example.bookxperttest.dbHelper.UserEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun saveUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }

    suspend fun getUser(): UserEntity? {
        return userDao.getUser()
    }
}