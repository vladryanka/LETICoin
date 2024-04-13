package com.example.leticoin.achievements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.leticoin.accounts.Account

@Entity(
    tableName = "achievements",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["username"],
        childColumns = ["username"],
        onDelete = ForeignKey.CASCADE // Define the action to take when the referenced entity is deleted
    )],
    indices = [Index("username")]
)
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "text")
    var text: String,
    @ColumnInfo(name = "priority")
    var priority: Int,
    @ColumnInfo(name = "username")
    var username: String
) {
    @Ignore
    constructor(text: String, priority: Int, username: String) : this(0, text, priority, username)

}