package com.example.leticoin.accounts

import android.util.Log
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "accounts")
data class Account(
    var id: Boolean,
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var password:String,
    var name: String

) {
    @Ignore
    constructor(id: Boolean, password: String, name: String) : this(id, "username", password, name){
        Log.d("Doing","Создаем аккаунт в Account 1")
    }

}
