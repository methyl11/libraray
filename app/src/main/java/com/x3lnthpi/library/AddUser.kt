package com.x3lnthpi.library


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.x3lnthpi.library.data.UserViewModel

@Composable
fun AddUsers() {

    //Access to viewModel
    val viewModel: UserViewModel = viewModel()


//    val context = LocalContext.current.applicationContext as Application
//    val viewModel: UserViewModel = viewModel(
//        factory = UserViewModelFactory(context)
//    )

    var text by remember { mutableStateOf("First Name") }
    var lastName by remember { mutableStateOf("Last Name") }
    var age by remember { mutableStateOf("10") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.Yellow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
            //  .background(color = Color.Blue)

        ) {
            Text("Filler text temporary")
            Text("Filler text temporary")
            Text("Filler text temporary")
            Text("Filler text temporary")
            Text("Filler text temporary")
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
//                viewModel.db.userDao().insertAll(
//                    User(
//                        text, lastName, age,
//                        age = 32
//                    )
//                )
            }) {
                Text("Submit to Room DB")
            }
        }
    }
}


@Preview
@Composable
fun ShowAddUser() {
    AddUsers()
}