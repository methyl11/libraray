package com.x3lnthpi.library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.x3lnthpi.library.data.User
import com.x3lnthpi.library.data.UserViewModel
import com.x3lnthpi.library.ui.theme.LibraryTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity3 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryTheme {

                val viewModel by viewModels<UserViewModel>()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting3(
                        name = "Kalimba",
                        modifier = Modifier.padding(innerPadding)
                    )


                    Column(modifier = Modifier.padding(20.dp)) {
                        //Testq(viewModel)
                        //DisplaySingleUser(viewModel)
                        GetAllUsers(viewModel, modifier = Modifier.padding(15.dp))
                    }



                }
            }
        }
    }
}

@Composable
fun Greeting3(
    name: String,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Green)
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    LibraryTheme {
        Greeting3("Android")
    }
}

@Composable
fun Testq(viewModel: UserViewModel) {
    var id by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("First Name") }
    var lastName by remember { mutableStateOf("Last Name") }
    var age by remember { mutableStateOf("10") }
    Box(
        modifier = Modifier
//            .fillMaxSize()
//            .fillMaxHeight()
//            .fillMaxWidth()
            .background(color = Color.Yellow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
            //  .background(color = Color.Blue)

        ) {
            TextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("ID") }
            )
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Label") }
            )
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Label") }
            )
            TextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Label") }
            )
            Button(onClick = {
                val user = User(
                    firstName = text,
                    lastName = lastName,
                    age = age.toIntOrNull() ?: 0,
                    uid = id
                )
                viewModel.insertUser(user)
            }) {
                Text("Submit to Room DB")
            }
        }
    }
}

@Composable
fun GetAllUsers(viewModel: UserViewModel, modifier: Modifier) {
    val userDao = viewModel.db.userDao()
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    Button(onClick = {
        CoroutineScope(Dispatchers.IO).launch {
            users = viewModel.db.userDao().getAll()
        }
    }) {
        Text("Get All users")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (users.isEmpty()) {
            item {
//                Text("")
//                Text("")
//                Text("")
//                Text("No users found")
            }
        } else {
            users.forEach { user ->
                item {
                    UserItem(user = user)
                }
            }

        }
    }
}


@Composable
fun UserItem(user: User) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Text("First Name: ${user.firstName ?: "Unknown"}")
        Text("Last Name: ${user.lastName ?: "Unknown"}")
        Text("Age: ${user.age ?: 0}")
        Divider(modifier = Modifier.padding(vertical = 4.dp))
    }
}

@Composable
fun DisplaySingleUser(viewModel: UserViewModel) {
    var user by remember { mutableStateOf<User?>(null) }

    // Fetch the first user when the composable loads
    LaunchedEffect(Unit) {
        user = viewModel.getFirstUser()
    }

    Button(onClick = {
        CoroutineScope(Dispatchers.IO).launch {
            user = viewModel.getUserById(910)
        }
    }) {
        Text("Fetch User")
    }

    // Display user details
    if (user != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("First Name: ${user!!.firstName}")
            Text("Last Name: ${user!!.lastName}")
            Text("Age: ${user!!.age}")
        }
    } else {
        Text("No user found", modifier = Modifier.padding(16.dp))
    }
}

