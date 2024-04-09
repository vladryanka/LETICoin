package com.example.leticoin
import android.app.Application
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.leticoin.accounts.Account
import com.example.leticoin.accounts.AccountsDao
import com.example.leticoin.achievements.Achievement
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.leticoin.achievements.AchievementsDao
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val accountDao: AccountsDao = AppDatabase.getInstance(application).accountsDao()
    private val achievementDao: AchievementsDao = AppDatabase.getInstance(application).achievementsDao()
    private val shouldCloseScreen: MutableLiveData<Boolean> = MutableLiveData()
    //private val appDatabase: AppDatabase = AppDatabase.getInstance(application)

//    fun getAchievements(): LiveData<List<Achievement>> {
//        return appDatabase.achievementsDao().getAchievements()
//    }
//    fun getAccounts(): LiveData<List<Account>> {
//        return appDatabase.accountsDao().getAccounts()
//    }
    fun findAccount(username:String): Account?{
        val accountLiveData = accountDao.searchAccount(username)
        var account: Account? = null
        val observer = Observer<Account> { accountValue ->
            account = accountValue
        }
        accountLiveData.observeForever(observer)
        accountLiveData.removeObserver(observer)
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
        viewModelScope.launch(Dispatchers.IO) {
            accountDao.add(account)
        }
    }
//    fun remove(achievement: Achievement) { // удаление достижения
//        viewModelScope.launch(Dispatchers.IO) {
//            appDatabase.achievementsDao().remove(achievement.id)
//        }
//    }
}
