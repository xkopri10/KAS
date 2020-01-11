package com.example.kas_project.database

import android.content.Context
import android.provider.DocumentsContract
import androidx.room.Room
import android.app.Application
import com.example.kas_project.models.ProfileKey
import com.example.kas_project.utils.GlobalApplication


class ProfileKeysDatabaseGetter {

    private fun getDatabase() : ProfileKeysDatabase {
        return Room.databaseBuilder(GlobalApplication.getAppContext(), ProfileKeysDatabase::class.java, "database").allowMainThreadQueries().build()
    }

    /**
     * Inserts a single task to the database
     */
    fun insert(task : ProfileKey):Long{
        return getDatabase().profileKeysDao().insert(task)
    }

    /**
     * Inserts a single profileKey to the database
     * @return the id of the newly added profileKey
     */
    fun getById(id : Long): ProfileKey{
        return getDatabase().profileKeysDao().findById(id)
    }

    /**
     * Updates a profileKey in the database
     */
    fun update(task: ProfileKey){
        getDatabase().profileKeysDao().update(task)
    }

    /**
     * Deletes a profileKeys from the database
     */
    fun delete(task: ProfileKey){
        getDatabase().profileKeysDao().delete(task)
    }

    /**
     * Returns all profileKeys from the database
     */
    fun getAll(): MutableList<ProfileKey>{
        return getDatabase().profileKeysDao().getAll()
    }

    /**
     * Returns all profileKeys from the database (only encryption people = friends FROM encryption section)
     */
    fun getAllEncryptPeople(): MutableList<ProfileKey>{
        return getDatabase().profileKeysDao().getAllEncryptPeople();
    }

    /**
     * Returns all profileKeys from the database (only encryption people = friends FROM decryption section)
     */
    fun getAllDecryptPeople(): MutableList<ProfileKey>{
        return getDatabase().profileKeysDao().getAllDecryptPeople();
    }



}