package com.example.kas_project.database

import androidx.room.*
import com.example.kas_project.models.ProfileKey

/**
 * Interface for manipulation with data (getting, inserting update, deleting and so on)
 * DB is based on DAO 
 */
@Dao
interface ProfileKeysDao {

    @Query("SELECT * FROM profileKeys")
    fun getAll(): MutableList<ProfileKey>

    @Query("SELECT * FROM profileKeys WHERE peopleForEncrypt = 1")
    fun getAllEncryptPeople(): MutableList<ProfileKey>

    @Query("SELECT * FROM profileKeys WHERE peopleForDecrypt = 1")
    fun getAllDecryptPeople(): MutableList<ProfileKey>

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