package com.example.leticoin.accounts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.leticoin.NameAndTotalPriority

@Dao
interface AccountsDao {
    @Query("SELECT * FROM accounts")
    fun getAccounts(): LiveData<List<Account>>

    @Insert
    fun add(account: Account)

    @Query("SELECT * FROM accounts WHERE username LIKE :searchQuery")
    fun searchAccount(searchQuery: String): Account?

    @Query("SELECT a.name, SUM(b.priority) as total_priority " +
            "FROM accounts a " +
            "LEFT JOIN achievements b ON a.username = b.username " +
            "WHERE a.teacher = 0 "+
            "GROUP BY a.username")
    fun getNameAndTotalPriority(): LiveData<List<NameAndTotalPriority>>

}
