package com.example.leticoin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leticoin.ui.theme.LETICoinTheme


class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var viewModel:MainViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)

        setContent {
            LETICoinTheme {
                navController = rememberNavController()

                Log.d("MainActivity", "Тут нет ошибки 4")
                MainScreen(navController, viewModel, this)
            }
        }

    }
}

@Composable
fun MainScreen(navController: NavHostController,viewModel: MainViewModel, mainActivity:MainActivity) {
    NavHost(navController = navController, startDestination = "greetingScreen") {
        composable("greetingScreen") {
            Screen.GreetingScreen(navController = navController)
        }
        composable("registrationScreen") {
            Screen.RegistrationScreen(navController = navController, viewModel)
        }
        composable("authorizationScreenGreeting") {
            Screen.AuthorizationScreenGreeting(navController, viewModel, mainActivity)
        }
        composable("achievementScreen") {
           // Screen.AchievementScreen(navController, viewModel, mainActivity)
        }
        composable("addAchievmentScreen") {
            Screen.AddAchievmentScreen(navController, viewModel)
        }
        composable("teacherScreen") {//teacher
            //AuthorizationScreenGreeting(navController)
        }
    }
}












@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LETICoinTheme {
        //Greeting("Android")
    }
}