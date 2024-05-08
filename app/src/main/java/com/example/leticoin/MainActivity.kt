package com.example.leticoin
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leticoin.ui.theme.LETICoinTheme
import androidx.lifecycle.ViewModelProvider
import com.example.leticoin.accounts.Account
import com.example.leticoin.achievements.Achievement

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var viewModel: MainViewModel
    private var accountsList: List<Account> = emptyList()
    private var achievementsList: List<Achievement> = emptyList()
    private var nameAndTotalPriority: List<NameAndTotalPriority> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this, ViewModelProvider
                .AndroidViewModelFactory.getInstance(application)
        )[MainViewModel::class.java]

        setContent {
            LETICoinTheme {
                Log.d("Doing", "Идем дальше")
                navController = rememberNavController()
                val owner = this

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
                        viewModel.getAchievements().observe(owner) { achievements ->
                            achievementsList = achievements
                        }
                        Screen.AchievementScreen(navController, achievementsList, viewModel)
                    }
                    composable("addAchievmentScreen") {
                        Screen.AddAchievmentScreen(navController, viewModel)
                    }
                    composable("teacherScreen") {
                        viewModel.getNameAndPriority().observe(owner) { nameAndPriority ->
                            nameAndTotalPriority = nameAndPriority
                        }
                        viewModel.getAccounts().observe(owner) { accounts ->
                            accountsList = accounts
                        }
                        Screen.TeacherScreen(navController,
                            nameAndTotalPriority,accountsList
                        )
                    }
                    composable("writeOffAchievementsScreen") {
                        viewModel.getAchievements().observe(owner) { achievements ->
                            achievementsList = achievements
                        }
                        Screen.WriteOffAchievementsScreen(achievementsList, viewModel,navController)
                    }
                }
            }
        }
    }
}