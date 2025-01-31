package com.x3lnthpi.library.views

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.request.DefaultRequestOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.x3lnthpi.library.SharedViewModel
import com.x3lnthpi.library.views.comonents.CategoryItem
import com.x3lnthpi.library.views.comonents.SaleItems

@Composable
fun HomeScreen(navController: NavController) {

    val sharedViewModel: SharedViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Nykaa Fashion", fontSize = 18.sp,
                fontWeight = FontWeight.Bold, color = Color.Black
            )

            //Right side icons
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourites",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.Black
                )
            }
        }

        //SearchBar
        TextField(
            value = "Search Nykaa",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp, // Left padding
                    end = 16.dp,   // Right padding
                    top = 7.dp     // Top padding) //Padding s added for spacing
                )
        )

        Card(
            elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier.padding(
                start = 5.dp,
                end = 5.dp,
                top = 10.dp
            ), shape = RectangleShape
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                CategoryItem(com.x3lnthpi.library.R.drawable.a, "Saraswati")
                CategoryItem(com.x3lnthpi.library.R.drawable.b, "Warrior")
                CategoryItem(com.x3lnthpi.library.R.drawable.c, "Blades")
                CategoryItem(com.x3lnthpi.library.R.drawable.d, "Fierce")
                CategoryItem(com.x3lnthpi.library.R.drawable.e, "Dancer")

            }
        }

        Button(onClick = {navController.navigate("VideoScreen")}) {
            Text("Watch Movie")
        }

        Card(
            elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier.padding(
                start = 5.dp,
                end = 5.dp,
                top = 10.dp
            ), shape = RectangleShape
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
                   // .padding(top = 7.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                SaleItems(com.x3lnthpi.library.R.drawable.q, "Purple", navController)
                SaleItems(com.x3lnthpi.library.R.drawable.w, "Pink", navController,)
                SaleItems(com.x3lnthpi.library.R.drawable.r, "Yellow", navController,)
                SaleItems(com.x3lnthpi.library.R.drawable.t, "Green", navController,)
                SaleItems(com.x3lnthpi.library.R.drawable.y, "Pink", navController,)
            }
        }

        Card(
            elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier.padding(
                start = 5.dp,
                end = 5.dp,
                top = 10.dp
            ), shape = RectangleShape
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
                    // .padding(top = 7.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                SaleItems(com.x3lnthpi.library.R.drawable.u, "orange", navController,)
                SaleItems(com.x3lnthpi.library.R.drawable.v, "Purple", navController,)
                SaleItems(com.x3lnthpi.library.R.drawable.x, "Black", navController,)
                SaleItems(com.x3lnthpi.library.R.drawable.z, "White", navController,)
                SaleItems(com.x3lnthpi.library.R.drawable.a1, "Pinkish", navController,)
                SaleItems(com.x3lnthpi.library.R.drawable.a2, "Vogue", navController,)
            }
        }
        //Button to navigate to different screen, temporary
        Button(onClick = { navController.navigate("PrintScreen") }) {
            Text("Go to Print screen")
        }
        //Button to navigate to SavedImages screen, temporary
        Button(onClick = { navController.navigate("SavedImagesScreen") }) {
            Text("Go to Saved Images")
        }
    }


}


@Preview
@Composable
fun HomePreview() {
    var navController = rememberNavController()
    HomeScreen(navController)

}


data class TabData(val title: String)

val tabs = listOf(
    TabData("Women"),
    TabData("Men"),
    TabData("Kids"),
    TabData("Home")
)


