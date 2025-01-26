package com.x3lnthpi.library.views.comonents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.x3lnthpi.library.SharedViewModel

@Composable
fun FullImageView(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier
) {
//    val selectedImageResourceState: State<Int?> = sharedViewModel.selectedImageResource.collectAsState()
//
//    // Access the value using .value
//    val selectedImageResource = selectedImageResourceState.value
//
//    selectedImageResource?.let { resourceId ->
//        Image(
//            painter = painterResource(id = resourceId), // Now within a @Composable
//            contentDescription = null,
//            contentScale = ContentScale.Fit,
//            modifier = modifier.fillMaxSize()
//        )
//    }
}
