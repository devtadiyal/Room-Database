package com.example.recodesave.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg user: User)


    @Query("SELECT * FROM employee ORDER BY id DESC")
    fun getList(): List<User>


}