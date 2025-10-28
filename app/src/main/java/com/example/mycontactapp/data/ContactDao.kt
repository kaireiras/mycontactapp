package com.example.mycontactapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {
//    interface untuk komunikasi data contact
//    biasanya disebut model dalam arsitektor MVC

    // buat fungsi untuk insert data
    @Insert
    suspend fun insert(contact: Contact): Long

    // fungsi untuk update
    @Update
    suspend fun update(contact: Contact)

    //fungsi untuk delete
    @Delete
    suspend fun delete(contact: Contact)

    //fungsi untuk query
    @Query("SELECT * FROM contacts ORDER BY name ASC")
    suspend fun getAll(): List<Contact>

}