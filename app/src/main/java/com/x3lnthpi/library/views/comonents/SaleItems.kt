package com.x3lnthpi.library.views.comonents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.x3lnthpi.library.SharedViewModel

@Composable
fun SaleItems(imageResource: Int, Details: String,navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
            //  .clip(shape = Shapes().medium)
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(4f / 3f)
                    .size(300.dp)
                    .clip(shape = RoundedCornerShape(1.dp))
                    .align(Alignment.Center)
                    .clickable(onClick = {navController.navigate("PrintScreen")})
            )
            Box(modifier = Modifier.align(Alignment.BottomCenter).offset(y = -20.dp)) {
                Text(Details, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
            }
        }
    }

}