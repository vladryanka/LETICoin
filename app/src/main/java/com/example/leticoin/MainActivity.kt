package com.example.leticoin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leticoin.ui.theme.LETICoinTheme


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.leticoin.accounts.Account
import com.example.leticoin.achievements.Achievement


class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var viewModel: MainViewModel
    private var accountsList: List<Account> = emptyList()
    private var achievementsList: List<Achievement> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(
            this, ViewModelProvider
                .AndroidViewModelFactory.getInstance(application)
        ).get(MainViewModel::class.java)

        setContent {
            LETICoinTheme {
                //val appDatabase = AppDatabase.getInstance(this)
                Log.d("Doing", "Идем дальше")
                navController = rememberNavController()
                viewModel.getAchievements().observe(this, Observer { achievements ->
                    achievementsList = achievements
                })
                viewModel.getAccounts().observe(this, Observer { accounts ->
                    accountsList = accounts
                })

                MainScreen(navController, viewModel,achievementsList,accountsList )
            }
        }

    }
}


@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    achievementsList: List<Achievement>,
    accountsList: List<Account>
) {
    NavHost(navController = navController, startDestination = "greetingScreen") {
        composable("greetingScreen") {
            Screen.GreetingScreen(navController = navController)
        }
        composable("registrationScreen") {
            Screen.RegistrationScreen(navController = navController, viewModel)

        }
        composable("authorizationScreenGreeting") {
            Screen.AuthorizationScreenGreeting(navController, viewModel)
        }
        composable("achievementScreen") {
            Screen.AchievementScreen(navController,viewModel, achievementsList)
        }
        composable("addAchievmentScreen") {
           // Screen.AddAchievmentScreen(navController)
        }
        composable("teacherScreen") {//teacher
            //TeacherScreen(navController)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LETICoinTheme {
        //Screen.AchievementScreen()
    }
}