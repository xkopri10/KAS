package com.example.kas_project.database

import android.content.Context
import android.provider.DocumentsContract
import androidx.room.Room
import android.app.Application
import com.example.kas_project.models.ProfileKey
import com.example.kas_project.utils.GlobalApplication


class ProfileKeysDatabaseGetter {

/*    private val sApplication: Application = Application()
    fun getApplication(): Application {
        return sApplication
    }

    fun getContext(): Context {
        return getApplication().applicationContext
    }*/

    private fun getDatabase() : ProfileKeysDatabase {
        return Room.databaseBuilder(GlobalApplication.getAppContext(), ProfileKeysDatabase::class.java, "database").build()
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



}