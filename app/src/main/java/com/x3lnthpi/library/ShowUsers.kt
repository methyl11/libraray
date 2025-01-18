package com.x3lnthpi.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.x3lnthpi.library.data.User

@Composable
fun ShowUsers() {
    //Access to viewModel
//    val viewModel : UserViewModel = viewModel()
//    val x = viewModel.db.userDao()
//    val users: List<User> = x.getAll()
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
           // users.forEach(users -> Text(users))
        }
    }
}


