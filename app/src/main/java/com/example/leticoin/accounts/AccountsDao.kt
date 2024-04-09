package com.example.leticoin.accounts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.leticoin.achievements.Achievement

@Dao
interface AccountsDao {
    @Query("SELECT * FROM accounts")
    fun getAccounts(): LiveData<List<Account>>

    @Insert
    fun add(account: Account)

    @Query("SELECT * FROM accounts WHERE username LIKE :searchQuery")
    fun searchAccount(searchQuery: String): LiveData<Account>

}
