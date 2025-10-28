package com.example.mycontactapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// ini adalah skema dari sebuah data, sekaligus sebagai blueprint sebuah data

@Entity(tableName = "contacts")
data class Contact(
    // buat primary key dengan nama id dan tipe data long lalu saya kasih default value 0
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val phone:String

)
