package com.example.leticoin.accounts

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "accounts")
public data class Account(
    var id: Boolean,
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var password:String,
    var name: String,
) {
    @Ignore
    constructor(id: Boolean, password: String, name: String) : this(id, "username", password, name)
}
