package com.example.leticoin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leticoin.ui.theme.LETICoinTheme


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leticoin.accounts.Account
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.leticoin.accounts.AccountsDao
import com.example.leticoin.achievements.Achievement
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(
            this, ViewModelProvider
                .AndroidViewModelFactory.getInstance(application)
        ).get(MainViewModel::class.java)

        setContent {
            LETICoinTheme {

                navController = rememberNavController()
                MainScreen(navController, viewModel, this)
            }
        }

    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    mainActivity: MainActivity
) {
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
            Screen.AchievementScreen(navController, viewModel)
        }
        composable("addAchievmentScreen") {
            Screen.AddAchievmentScreen(navController, viewModel)
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
        //Greeting("Android")
    }
}