package com.example.mycontactapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

// app database
//digunakan untuk source of database dari aplikasi
@Database(entities = [Contact::class], version =  1, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object{

        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase = INSTANCE?: synchronized(this){
            INSTANCE?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "contacts.db"
            ).build().also{ INSTANCE = it }
        }
    }
}