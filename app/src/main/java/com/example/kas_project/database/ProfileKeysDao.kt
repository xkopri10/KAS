package com.example.kas_project.database

import androidx.room.*
import com.example.kas_project.models.ProfileKey

@Dao
interface ProfileKeysDao {

    @Query("SELECT * FROM profileKeys")
    fun getAll(): MutableList<ProfileKey>

    @Query("SELECT * FROM profileKeys WHERE id = :id")
    fun findById(id : Long): ProfileKey

    // Returning Long value. It is id of the newly added task.
    @Insert
    fun insert(task: ProfileKey): Long

    @Update
    fun update(task: ProfileKey)

    @Delete
    fun delete(task: ProfileKey)

}