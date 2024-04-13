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
import androidx.lifecycle.ViewModelProvider
import com.example.leticoin.accounts.Account


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
                //val appDatabase = AppDatabase.getInstance(this)
                Log.d("Doing", "Идем дальше")
                navController = rememberNavController()

                MainScreen(navController, viewModel)
            }
        }

    }
}
//appDatabase:AppDatabase
@Composable
fun TestFun(appDatabase:AppDatabase){
    var textStateName by remember { mutableStateOf(TextFieldValue()) }
    var textStatePassword by remember { mutableStateOf(TextFieldValue()) }


    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column {
        TextField(
            value = textStateName,
            onValueChange = {
                textStateName = it
                username = it.text.trim()
            },
            label = {
                Text(
                    stringResource(id = R.string.usernameID),
                    color = Color.Black
                )
            },
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = textStatePassword,
            onValueChange = {
                textStatePassword = it
                password = it.text.trim()
            },
            label = {
                Text(
                    stringResource(id = R.string.password),
                    color = Color.Black
                )
            },
            modifier = Modifier.padding(8.dp)
        )
        Button(onClick = {
            Thread {
                appDatabase.accountsDao().add(Account(false,name = "Joe", username = username, password = password))
            }.start()

        }) {
            Text(text = "добавить")

        }
    }


}

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
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
            Screen.AchievementScreen(navController,viewModel)
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