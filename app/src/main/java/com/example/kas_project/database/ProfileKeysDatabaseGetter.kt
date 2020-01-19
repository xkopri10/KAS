package com.example.kas_project.database

import androidx.room.Room
import com.example.kas_project.models.ProfileKey
import com.example.kas_project.utils.GlobalApplication


class ProfileKeysDatabaseGetter {

    private fun getDatabase() : ProfileKeysDatabase {
        return Room.databaseBuilder(GlobalApplication.getAppContext(), ProfileKeysDatabase::class.java, "database").allowMainThreadQueries().build()
    }

    /**
     * Inserts a single profileKey to the database
     */
    fun insert(profileKey : ProfileKey):Long{
        return getDatabase().profileKeysDao().insert(profileKey)
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
    fun update(profileKey: ProfileKey){
        getDatabase().profileKeysDao().update(profileKey)
    }

    /**
     * Deletes a profileKeys from the database
     */
    fun delete(profileKey: ProfileKey){
        getDatabase().profileKeysDao().delete(profileKey)
    }

    /**
     * Returns all profileKeys from the database
     */
    fun getAll(): MutableList<ProfileKey>{
        return getDatabase().profileKeysDao().getAll()
    }

    /**
     * Returns all profileKeys from the database (only encryption people = friends FROM ENCRYPTION SECTION)
     */
    fun getAllEncryptPeople(): MutableList<ProfileKey>{
        return getDatabase().profileKeysDao().getAllEncryptPeople()
    }

    /**
     * Returns all profileKeys from the database (only encryption people = friends FROM DECRYPTION SECTION)
     */
    fun getAllDecryptPeople(): MutableList<ProfileKey>{
        return getDatabase().profileKeysDao().getAllDecryptPeople()
    }



}