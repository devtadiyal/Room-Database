package com.example.recodesave.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class User(
    var name: String,
    var email: String,
    var salary: String,
    var address: String,
    var department: String,
    var joiningDate: String,
    var phoneNo: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
