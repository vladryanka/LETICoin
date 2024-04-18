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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.toSize
import com.example.leticoin.accounts.Account
import com.example.leticoin.achievements.Achievement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow

class Screen {

    companion object {
        private var currentUsername = ""

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
        ) {

            var stateRadioButton by remember { mutableStateOf(true) }
            var isTeacherSelected by remember { mutableStateOf(true) }
            var textStateUsername by remember { mutableStateOf(TextFieldValue()) }
            var textStateName by remember { mutableStateOf(TextFieldValue()) }
            var textStatePassword by remember { mutableStateOf(TextFieldValue()) }
            var textStateReenterPassword by remember { mutableStateOf(TextFieldValue()) }
            val snackbarHostState = remember { SnackbarHostState() }
            var passwordVisible by remember { mutableStateOf(false) }
            var reenteredPasswordVisible by remember { mutableStateOf(false) }

            val scope = rememberCoroutineScope()

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
                        modifier = Modifier.padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words
                        )
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
                        modifier = Modifier.padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default
                            .copy(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = {
                                passwordVisible = !passwordVisible
                            }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        }

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
                        modifier = Modifier.padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default
                            .copy(keyboardType = KeyboardType.Password),
                        visualTransformation = if (reenteredPasswordVisible)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (reenteredPasswordVisible)
                                Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = {
                                reenteredPasswordVisible = !reenteredPasswordVisible
                            }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        }

                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Log.d("Doing", "Мы создали экран регистрации")
                    Button(
                        onClick = {
                            if (username.equals("") || name.equals("")
                                || password.equals("") || reenteredPassword.equals("")
                            ) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Заполните все поля",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } else {
                                if (reenteredPassword != password)
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Пароли не совпадают",
                                            withDismissAction = true,
                                            duration = SnackbarDuration.Short
                                        )
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
        ) {
            var textStateUsername by remember { mutableStateOf(TextFieldValue()) }
            var textStatePassword by remember { mutableStateOf(TextFieldValue()) }
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            val snackbarHostState = remember { SnackbarHostState() }
            var passwordVisible by remember { mutableStateOf(false) }
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
                        modifier = Modifier.padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default
                            .copy(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = {
                                passwordVisible = !passwordVisible
                            }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                val account = viewModel.findAccount(username)

                                if (account == null) {
                                    withContext(Dispatchers.Main) {
                                        snackbarHostState.showSnackbar(
                                            "Введен некорректный username",
                                            withDismissAction = true,
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                } else {
                                    if (account.password != password) {
                                        withContext(Dispatchers.Main) {
                                            snackbarHostState.showSnackbar(
                                                "Введен некорректный пароль",
                                                withDismissAction = true,
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    } else {
                                        currentUsername = account.username
                                        if (account.teacher == true)
                                            withContext(Dispatchers.Main) {
                                                navController.navigate("teacherScreen") {
                                                    Log.d("Doing", "teacherScreen")
                                                    popUpTo("teacherScreen") {
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                        else
                                            withContext(Dispatchers.Main) {
                                                navController.navigate("achievementScreen") {
                                                    Log.d("Doing", "achievementScreen")
                                                    popUpTo("achievementScreen") {
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                    }
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
            achievementsList: List<Achievement>,
            viewModel: MainViewModel
        ) {
            var sum = 0
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
                var achievementsListToUser = emptyList<Achievement>()
                for (item in achievementsList) {
                    if (item.username == currentUsername) {
                        sum += item.priority
                        achievementsListToUser += item
                    }
                }
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 80.dp)
                ) {
                    itemsIndexed(
                        achievementsListToUser
                    ) { _, item ->
                        Row {
                            Card(modifier = Modifier.weight(1f)) {

                                Row(
                                    modifier = Modifier
                                        .height(80.dp)
                                        .padding(start = 15.dp, end = 15.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.text,
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Start,
                                        maxLines = 1, // Ограничиваем количество строк
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = item.priority.toString(),
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        viewModel.remove(item)
                                    }
                                    navController.navigate("achievementScreen") {
                                        popUpTo("achievementScreen") {
                                            inclusive = true
                                        }
                                    }

                                },
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            ), // Отступы справа и снизу по 16dp
                        horizontalArrangement = Arrangement.End, // Размещаем элементы в конце горизонтально
                        verticalAlignment = Alignment.Bottom // Выравниваем по нижнему краю
                    ) {
                        Text(
                            text = stringResource(R.string.yourSum, sum), // сделать sum LiveData
                            modifier = Modifier.weight(1f),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Start
                        )
                        FloatingActionButton(
                            shape = CircleShape,
                            modifier = Modifier.padding(start = 16.dp),
                            onClick = {
                                navController.navigate("addAchievmentScreen")
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
        fun AddAchievmentScreen(
            navController: NavHostController,
            viewModel: MainViewModel
        ) {
            var textStateAchievementName by remember { mutableStateOf(TextFieldValue()) }
            var achievementName by remember { mutableStateOf("") }
            var achievementType by remember { mutableStateOf("") }


            val snackbarHostState = remember { SnackbarHostState() }

            val scope = rememberCoroutineScope()

            var expanded by remember { mutableStateOf(false) }
            val suggestions = listOf(
                stringResource(id = R.string.olympics),
                stringResource(id = R.string.article),
                stringResource(id = R.string.conference)
            )
            var selectedText by remember { mutableStateOf("") }

            var textfieldSize by remember { mutableStateOf(Size.Zero) }

            val icon = if (expanded)
                Icons.Filled.KeyboardArrowUp
            else
                Icons.Filled.KeyboardArrowDown

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
                                            navController.navigate("achievementScreen") {
                                                popUpTo("achievementScreen")
                                            }
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

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 80.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        value = textStateAchievementName,
                        onValueChange = {
                            textStateAchievementName = it
                            achievementName = it.text.trim()
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.enter_the_achievement),
                                color = Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))


                    OutlinedTextField(
                        value = selectedText,
                        onValueChange = { selectedText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                //This value is used to assign to the DropDown the same width
                                textfieldSize = coordinates.size.toSize()
                            },
                        label = { Text("Выберите тип достижения") },
                        trailingIcon = {
                            Icon(icon, "contentDescription",
                                Modifier.clickable { expanded = !expanded })
                        }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    ) {
                        suggestions.forEach { label ->
                            DropdownMenuItem(text = { Text(label) }, onClick = {
                                selectedText = label
                                expanded = false
                                achievementType = label
                            })
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            var sum = 0
                            if (achievementType.equals("") || achievementName.equals("")) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Заполните все поля",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } else {
                                sum = if (achievementType.equals(suggestions[0]))
                                    50
                                else if (achievementType.equals(suggestions[1]))
                                    20
                                else 30


                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.saveAchievement(
                                        Achievement(
                                            achievementName, sum, currentUsername
                                        )
                                    )
                                }

                                navController.navigate("achievementScreen") {
                                    popUpTo("achievementScreen") {
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            stringResource(id = R.string.save), color = Color.Black,
                            textAlign = TextAlign.Center, style = TextStyle(fontSize = 24.sp)
                        )
                    }
                }
            }
        }


        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun TeacherScreen(
            navController: NavHostController,
            achievementsList: List<Achievement>,
            accountsList: List<Account>,
            viewModel: MainViewModel
        ) {
            var textSeachName by remember { mutableStateOf(TextFieldValue()) }
            var searchName by remember { mutableStateOf("все") }
            var sum by remember { mutableStateOf(0) }
            var title ="name"
            val account = (accountsList.find { it.username == currentUsername })
            if (account!= null) title =account.name

            var accountsListCard: MutableList<Account> = accountsList.toMutableList()
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
                                Text(
                                    title,
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
                )
                {
                    TextField(
                        value = textSeachName,
                        onValueChange = {
                            textSeachName = it
                            searchName = it.text.trim()
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.enter_the_student),
                                color = Color.Black
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        trailingIcon = {
                            // IconButton с иконкой лупы
                            IconButton(onClick = {
                                if (searchName.equals("все"))
                                    accountsListCard = accountsList.toMutableList()
                                else {
                                    accountsListCard.clear()
                                    CoroutineScope(Dispatchers.IO).launch {
                                        val account = viewModel.findAccount(searchName)
                                        if (account!=null)
                                            accountsListCard.add(account)
                                    }
                                }


                            }) {
                                Icon(Icons.Default.Search, contentDescription = "Search Icon")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    )
                    {
                        itemsIndexed(
                            accountsListCard
                        ) { _, item ->

                            for (it in achievementsList) {
                                if (it.username == item.username) {
                                    sum += it.priority
                                }
                            }
                            Log.d("Doing","$sum")

                            Row {
                                Card(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier
                                            .height(80.dp)
                                            .padding(start = 15.dp, end = 15.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = item.name,
                                            modifier = Modifier.padding(horizontal = 16.dp),
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Start,
                                            maxLines = 1, // Ограничиваем количество строк
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Text(
                                            text = sum.toString(),
                                            modifier = Modifier.padding(horizontal = 16.dp),
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.End
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            sum = 0
                        }

                    }

                }
            }
        }
    }
}

/*LazyColumn {
                                        itemsIndexed(
                                            achievementsListToUser
                                        ) { _, achieve ->
                                            Row {
                                                Text(
                                                    text = achieve.text,
                                                    fontSize = 18.sp
                                                )
                                                Spacer(modifier = Modifier.weight(1f))
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Text(
                                                    text = achieve.priority.toString(),
                                                    fontSize = 18.sp
                                                )
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Checkbox(
                                                    checked = checked,
                                                    onCheckedChange = {
                                                        checked = it
                                                    }
                                                )
                                            }

                                        }*/