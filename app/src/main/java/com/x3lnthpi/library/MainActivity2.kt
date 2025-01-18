package com.x3lnthpi.library

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.x3lnthpi.library.ui.theme.LibraryTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryTheme {

                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    DisplayFamily(familyMembers, navController)

                    val context = this
                    val appContext = applicationContext
                    val bsCon = this.baseContext
                    //      val bcx = ContextWrapper.baseContext
                    val cc = ContextWrapper(context)
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier.fillMaxSize(1f)) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun LearningState() {
    var textState by remember { mutableStateOf(4) }
    Text(familyMembers.get(2), modifier = Modifier.background(color = Color.Red))
}

@Preview
@Composable
fun DispLS() {
    LearningState()
}


@Composable
fun Check(clsMem: List<A>) {
    Column {
        for (item in clsMem) {
            Text(item.age.toString())
        }
        LearningState()
    }
}

@Preview
@Composable
fun CheckM() {
    val a = A(5)
    val b = A(6)
    val c = A(7)
    val clasMem = mutableListOf<A>(a, b, c)
    Check(clasMem)
}

@Composable
fun DisplayFamily(familyMembers: List<String>?, navController: NavController) {
    Column(
        modifier = Modifier
            .background(color = Color.Green)
            .blur(0.0.dp)
    ) {
        if (familyMembers != null) {
            for (items in familyMembers) {
                Text(items)
                Divider(color = Color.Black)
            }
        }
        var someVal = remember { mutableStateOf("Rahul") }
        var someVal2 = remember { mutableStateOf("56.76") }
        Text(someVal2.value)
        Divider(color = Color.Black)
        Text(someVal.value)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    LibraryTheme {
        Greeting2("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayFamily() {
    // DisplayFamily(familyMembers)
}