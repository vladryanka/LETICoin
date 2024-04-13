package com.example.leticoin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.leticoin.accounts.AccountsDao
import com.example.leticoin.accounts.Account
import com.example.leticoin.achievements.AchievementsDao
import com.example.leticoin.achievements.Achievement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val accountDao: AccountsDao = AppDatabase.getInstance(application).accountsDao()
    private val achievementDao: AchievementsDao = AppDatabase.getInstance(application).achievementsDao()
    private val shouldCloseScreen: MutableLiveData<Boolean> = MutableLiveData()
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)

    fun getAchievements(): LiveData<List<Achievement>> {
        return appDatabase.achievementsDao().getAchievements()
    }
    fun getAccounts(): LiveData<List<Account>> {
        return appDatabase.accountsDao().getAccounts()
    }
    fun findAccount(username:String): Account?{
        val account = accountDao.searchAccount(username)

        return account
    }

    fun getShouldCloseScreen(): LiveData<Boolean> {
        return shouldCloseScreen
    }

    fun saveAchievement(achievement: Achievement) {
        viewModelScope.launch(Dispatchers.IO) {
            achievementDao.add(achievement)
            shouldCloseScreen.postValue(true)
        }
    }
    fun saveAccount(account: Account) {
        //viewModelScope.launch(Dispatchers.IO) {
            Log.d("Doing","Пришли в saveAccount")
            accountDao.add(account)
       // }
    }
    fun remove(achievement: Achievement) { // удаление достижения
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.achievementsDao().remove(achievement.id)
        }
    }
}
