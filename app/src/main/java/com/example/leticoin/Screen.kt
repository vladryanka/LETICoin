package com.example.leticoin

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.text.input.KeyboardType
import com.example.leticoin.accounts.Account
import com.example.leticoin.achievements.Achievement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Screen {
    companion object {
        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun GreetingScreen(navController: NavHostController) { // экран добро пожаловать
            Scaffold(
                topBar = @Composable {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(
                                stringResource(R.string.greeting), color = Color.Black,
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            )
                        }
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(
                            id = R.string.app_name
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = { navController.navigate("authorizationScreenGreeting") }) {
                        Text(
                            text = stringResource(id = R.string.enter), color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = { navController.navigate("registrationScreen") }) {
                        Text(
                            text = stringResource(id = R.string.registers), color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                        )
                    }
                }
            }
        }

        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun RegistrationScreen(
            navController: NavHostController, viewModel: MainViewModel
        ) { // экран регистрации

            var stateRadioButton by remember { mutableStateOf(true) }
            var isTeacherSelected by remember { mutableStateOf(true) }
            var textStateUsername by remember { mutableStateOf(TextFieldValue()) }
            var textStateName by remember { mutableStateOf(TextFieldValue()) }
            var textStatePassword by remember { mutableStateOf(TextFieldValue()) }
            var textStateReenterPassword by remember { mutableStateOf(TextFieldValue()) }
            var snackbarHostState = remember { SnackbarHostState() }

            var scope = rememberCoroutineScope()


            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var reenteredPassword by remember { mutableStateOf("") }
            var name by remember { mutableStateOf("") }

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = @Composable {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(1f)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.backnew_image),
                                    contentDescription = stringResource(
                                        R.string.back
                                    ), modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            navController.navigate("greetingScreen")
                                        }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    stringResource(R.string.registers),
                                    color = Color.Black,
                                    maxLines = 1,
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle(fontSize = 24.sp)
                                )
                            }
                        }
                    )
                }
            ) {


                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 80.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        modifier = Modifier.selectableGroup()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isTeacherSelected,
                                onClick = {
                                    isTeacherSelected = true
                                    stateRadioButton = true
                                }
                            )
                            Text(
                                text = stringResource(id = R.string.teacher),
                                fontSize = 28.sp,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = !isTeacherSelected,
                                onClick = {
                                    isTeacherSelected = false
                                    stateRadioButton = false
                                }
                            )
                            Text(
                                text = stringResource(id = R.string.student),
                                fontSize = 28.sp,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = textStateUsername,
                        onValueChange = {
                            textStateUsername = it
                            username = it.text.trim()
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.usernameID),
                                color = Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = textStateName,
                        onValueChange = {
                            textStateName = it
                            name = it.text.trim()
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.username),
                                color = Color.Black
                            )
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

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
                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = textStateReenterPassword,
                        onValueChange = {
                            textStateReenterPassword = it
                            reenteredPassword = it.text.trim()
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.reenter_the_password),
                                color = Color.Black
                            )
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Log.d("Doing","Мы создали экран регистрации")
                    Button(
                        onClick = {
                            if (username.equals("") || name.equals("")
                                || password.equals("") || reenteredPassword.equals("")) {
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        "Заполните все поля",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short)
                                }
                            }
                            else{
                                if (reenteredPassword != password)
                                    scope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            "Пароли не совпадают",
                                            withDismissAction = true,
                                            duration = SnackbarDuration.Short)
                                    }

                                else {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        viewModel.saveAccount(
                                            Account(
                                                isTeacherSelected, username, password, name
                                            )
                                        )
                                    }

                                    navController.navigate("greetingScreen") {
                                        popUpTo("greetingScreen") {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            stringResource(id = R.string.save),
                            color = Color.Black, textAlign = TextAlign.Center,
                            style = TextStyle(fontSize = 24.sp)
                        )
                    }
                }
            }

        }


        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun AuthorizationScreenGreeting(
            navController: NavHostController,
            viewModel: MainViewModel
        ) { // экран авторизации

            var textStateUsername by remember { mutableStateOf(TextFieldValue()) }
            var textStatePassword by remember { mutableStateOf(TextFieldValue()) }
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = @Composable {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(1f)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.backnew_image),
                                    contentDescription = stringResource(
                                        R.string.back
                                    ), modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            navController.navigate("greetingScreen")
                                        }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    stringResource(R.string.authorization),
                                    color = Color.Black,
                                    maxLines = 1,
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle(fontSize = 24.sp)
                                )
                            }
                        }
                    )
                }
            ) {

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 80.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        value = textStateUsername,
                        onValueChange = {
                            textStateUsername = it
                            username = it.text.trim()
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.enter_the_username),
                                color = Color.Black
                            )
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = textStatePassword,
                        onValueChange = {
                            textStatePassword = it
                            password = it.text.trim()
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.enter_the_password),
                                color = Color.Black
                            )
                        },
                        modifier = Modifier.padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            var canMove = 3
                            CoroutineScope(Dispatchers.IO).launch {
                                var account = viewModel.findAccount(username)
                                if (account!=null){
                                    Log.d("Doing","${account.name}")
                                    Log.d("Doing","${account.username}")
                                    Log.d("Doing","${account.password}")
                                    Log.d("Doing","${account.teacher}")}
                                else Log.d("Doing","Account = NULL")
                                if (account == null) {
                                    scope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            "Введен некорректный username",
                                            withDismissAction = true,
                                            duration = SnackbarDuration.Short)
                                    }
                                }
                                else{
                                    if (account.password!=password)
                                        scope.launch {
                                            val result = snackbarHostState.showSnackbar(
                                                "Введен некорректный пароль",
                                                withDismissAction = true,
                                                duration = SnackbarDuration.Short)
                                        }
                                    else {
                                        if (account.teacher == true)
                                            canMove = 0
                                        else
                                            canMove = 1

                                    }
                                }
                            }
                            when (canMove) {
                                0 -> {
                                    navController.navigate("teacherScreen") {
                                        popUpTo("teacherScreen") {
                                            inclusive = true
                                        }
                                    }
                                }
                                1 -> {
                                    navController.navigate("achievementScreen") {
                                        popUpTo("achievementScreen") {
                                            inclusive = true
                                        }
                                    }
                                }
                                else -> {
                                    Log.d("Doing","ошибка в аккаунте")
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            stringResource(id = R.string.enter), color = Color.Black,
                            textAlign = TextAlign.Center, style = TextStyle(fontSize = 24.sp)
                        )
                    }

                }
            }

        }

        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun AchievementScreen(
            navController: NavHostController,
            viewModel: MainViewModel
        ) {//AchievementScreen
            var sum: Int = 0
            var achievements by remember { mutableStateOf<List<Achievement>>(emptyList()) }
//            viewModel.getAchievements().observe(mainActivity) { newAchievements ->
//                achievements = newAchievements
//            }
            Scaffold(
                topBar = @Composable {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(
                                stringResource(R.string.achievements),
                                color = Color.Black,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(fontSize = 24.sp), textAlign = TextAlign.Center
                            )

                        }
                    )
                }
            ) {

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 80.dp)
                ) {
                    itemsIndexed(
                        achievements
                    ) { _, item ->
                        sum += item.priority
                        Card(
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 15.dp, end = 15.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.text,
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Start
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = item.priority.toString(),
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 20.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.yourSum, sum),
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.weight(1f)) // Add a Spacer to push the FloatingActionButton to the right
                        FloatingActionButton(
                            shape = CircleShape,
                            onClick = {
                                // Handle FloatingActionButton click
                            }
                        ) {
                            Icon(Icons.Default.Add, stringResource(id = R.string.add_achievement))
                        }
                    }
                }
            }
        }


        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun AddAchievmentScreen(navController: NavHostController,appDatabase: AppDatabase) {//
            Scaffold(
                topBar = @Composable {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(1f)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.backnew_image),
                                    contentDescription = stringResource(
                                        R.string.back
                                    ), modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            //navController.navigate("s1")
                                        }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    stringResource(R.string.add_achievement),
                                    color = Color.Black,
                                    maxLines = 1,
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle(fontSize = 24.sp)
                                )
                            }
                        }
                    )
                }
            ) {

                val textState = remember { mutableStateOf(TextFieldValue()) }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 80.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        value = textState.value, onValueChange = { newValue ->
                            textState.value = newValue
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.enter_the_achievement),
                                color = Color.Black
                            )
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))


                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            stringResource(id = R.string.enter), color = Color.Black,
                            textAlign = TextAlign.Center, style = TextStyle(fontSize = 24.sp)
                        )
                    }
                }
            }
        }
    }

}