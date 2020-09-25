package com.example.recodesave.repository

import com.example.recodesave.roomdb.AppDatabase
import com.example.recodesave.roomdb.User

class UserRepository(private val db: AppDatabase) {
    suspend fun saveUser(user: User) = db.getUserDao().insert(user)
    suspend fun deleteUser(user: User) = db.getUserDao().deleteUser(user)
    suspend fun updateUser(user: User) = db.getUserDao().updateUser(user)
    fun getUser() = db.getUserDao().getList()
}
