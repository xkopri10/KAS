package com.example.kas_project.models

import androidx.room.*

@Entity(tableName = "profileKeys")
data class ProfileKey(@ColumnInfo(name = "name")var name: String?) {

    constructor() : this(null)

    // the database id. It is a instance of the class to be
    // able to set it null and check for it
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Long? = null

    @ColumnInfo(name = "email")
    var email : String? = null

    @ColumnInfo(name = "pParameter")
    var pParameter : String? = null

    @ColumnInfo(name = "qParameter")
    var qParameter : String? = null

    @ColumnInfo(name = "nParameter")
    var nParameter : String? = null

    @ColumnInfo(name = "eParameter")
    var eParameter : String? = null

    @ColumnInfo(name = "dParameter")
    var dParameter : String? = null
}

