package com.example.kas_project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kas_project.models.ProfileKey

@Database(entities = [ProfileKey::class], version = 2, exportSchema = true)
abstract class ProfileKeysDatabase : RoomDatabase() {
    abstract fun profileKeysDao() : ProfileKeysDao
}