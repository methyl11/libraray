package com.x3lnthpi.library

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.core.Dialog
import com.composables.core.rememberDialogState
import com.x3lnthpi.library.ui.theme.LibraryTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.getSystemService
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
//import androidx.privacysandbox.tools.core.generator.build
import com.composables.core.ModalBottomSheet
import com.composables.core.ModalBottomSheetState
import com.composables.core.SheetDetent
import com.composables.core.rememberModalBottomSheetState
import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI
import com.x3lnthpi.library.data.AppDatabase
import com.x3lnthpi.library.gems.DaggerGemsSpawnerComponent
import com.x3lnthpi.library.gems.GemsModule
import com.x3lnthpi.library.magic.DaggerMagicComponent
import com.x3lnthpi.library.magic.MagicComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
//import org.openjdk.tools.doclint.Entity.Dagger
import java.util.Locale



class MainActivity : ComponentActivity() {

    val component = DaggerUserRegistration.builder().build()
    val spawn = DaggerSpawnerComponent.builder().build()
   // val gem = DaggerGemsSpawnerComponent.builder().gemsModule(GemsModule(4)).build()
    val gem = DaggerGemsSpawnerComponent.factory().create(10)

    val magic = DaggerMagicComponent.builder().build()

  //  val magicalComp = (application as MagicComponent)
 //   val magicalComp2 = (application as MagicComponent)

   // val magicalComp3 = (application as MagicComponent).fire()
 //   val magicalComp4 = (application as MagicComponent).fire()

    val userRegistrationService = component.getUserRegistrationService().registerUser("rt", "jj")
    val emailService = component.getEmailService().send("a", "b", "c")


    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val vibratorHelper = VibratorHelper(this)

        val enemyHealth = spawn.spawnEnemy().attack(387.96f)
        val weaponDamage = spawn.spawnWeapon().doDamage(35.76f)

        magic.conjure().castSpell()


        setContent {
            LibraryTheme {

                val eH by remember { mutableStateOf(spawn.spawnEnemy().attack(387.96f)) }
                val wD by remember { mutableStateOf(spawn.spawnWeapon().doDamage(35.76f)) }
//            val db = Room.databaseBuilder(
//                applicationContext,
//                AppDatabase::class.java, "User"
//            ).build()

                //Context
                val context = this
                val contextLocal = LocalContext.current


                //For Navigation Drawer, it takes DrawerState as input parameter
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope1 = rememberCoroutineScope()






            //Getting text/resource for our TTS operation
            val welcome_message = getString(R.string.welcome_message)
            val closing_message = getString(R.string.closing_message)

            //Get hindi text
            val msg1 = getString(R.string.hindi1)
            val msg2 = getString(R.string.hindi2)

            //Initialize the TTS Engine
            var textToSpeech: TextToSpeech? = null
            textToSpeech = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    // Set the language
                    val result = textToSpeech?.setLanguage(Locale("hi", "IN"))

                    // Check if the language is supported
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        println("Hindi language is not supported on this device")
                    } else {
                        println("TTS is ready to use")
                        textToSpeech?.speak(
                            "नमस्ते दुनिया!", // Hindi text
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    }
                } else {
                    println("Initialization of TTS failed")
                }
            })
//                {
//                    status -> if(status == TextToSpeech.SUCCESS){
//                        textToSpeech?.language = Locale("hi", "IN")
//                    }
//                }


            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                TopAppBar(title = { Text("Kajal Yoga Classes") },
                    colors = TopAppBarDefaults.topAppBarColors(Color.Blue),
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                        IconButton(onClick = {
                            scope1.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "shopping cart desc"
                            )
                        }
                    })
            }, bottomBar = {
                BottomAppBar(actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = "Share Localized description"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Build,
                            contentDescription = "BUild Localized Settings"
                        )
                    }
                }, floatingActionButton = {
                    FloatingActionButton(
                        onClick = {},
                        containerColor = Color.Blue,
                        elevation = FloatingActionButtonDefaults.elevation(16.dp)
                    ) {
                        Icon(
                            Icons.Filled.Call,
                            contentDescription = "call Localized description"
                        )
                    }
                })
            }) { innerPadding ->

                //For Bottom Sheet
                val sheetState = rememberModalBottomSheetState(SheetDetent.FullyExpanded)
                val scope = rememberCoroutineScope()
                var showBottomSheet by remember { mutableStateOf(false) }
                // var sS = ModalBottomSheetState()
                //Bottom Sheet ends

                //val state = rememberScrollState()

                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                    //For navigating
                    val navController = rememberNavController()

//                    NavHost(navController = navController, startDestination = "navDrawEx") {
//                        composable("navDrawEx") {
//                            NavDrawEX(drawerState, navController)
//                        }
//                        composable("DisplayFamily"){
//                            DisplayFamily(familyMembers,navController)
//                        }
//                        composable("greeting"){
//                            Greeting(name = "Android")
//                        }
//                    }


                    //Alternate NavHost for getting to other screens
                    NavHost(navController = navController, startDestination = "Homie"){
                        composable("Homie"){
                            Homie(navController)
                        }
                        composable("addUser"){
                            //MainActivity3()
                            //Testq()
                            NavigateToMainActivity3(navController, contextLocal)
                        }
                        composable("showUsers"){
                            ShowUsers()
                        }
                    }


                    Greeting(
                        name = "Android", modifier = Modifier.padding(innerPadding)
                    )
                    VertexAITest("Astrology k baare m kuch batao")

                    Text("Enemy Health is" + eH)
                    Text("Weapon Damage is" + wD)

                    Homie(navController)

                    NavigateToMainActivity3(navController, contextLocal)

//                    DialogueTest()
//                    VibrateMyPhone(vibratorHelper)
//                    Button(onClick = { showBottomSheet = true }) {
//                        Text("Show Bottom Sheet")
//                    }

                    ////////////////////////

//                    if (showBottomSheet) {
//                        ModalBottomSheet(
//                            onDismiss = {
//                                println("onDismissRequest triggered")
//                                showBottomSheet = false
//                            }, state = sheetState
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .fillMaxHeight()
//                                    //.heightIn(min = 100.dp, max = 300.dp)//Set a height range
//                                    //.background(Color.Yellow)
//                                    .padding(16.dp)
//                            ) {
//                                Column(
//                                    modifier = Modifier
//                                        .align(Alignment.BottomStart)
//                                        .background(Color.White)
//                                        .heightIn(min = 100.dp, max = 300.dp)
//                                        .fillMaxWidth()
//                                        .padding(16.dp)
//                                ) {
//                                    Text("This is a Modal Bottom Sheet")
//                                    Spacer(Modifier.height(16.dp))
//                                    Button(onClick = { showBottomSheet = false }) {
//                                        Text("Hide Bottom sheet")
//                                    }
//                                }
//                            }
//
//                        }
//                    }

                    ///////////////

//                    DispatcherExample()
//                    DoSpeechSynth(
//                        onSpeak = { text ->
//                            val params = Bundle()
//                            // params.putInt(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "utteranceId") // Optional utterance ID
//                            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, params, null)
//                        },
//                        onStop = { textToSpeech.stop() },
//                        welcomeMessage = welcome_message,
//                        closingMessage = closing_message
//                    )

                   // GetHindiTTSPack(context) //Download hindi pack

                    //For hindi dialog
//                    DoSpeechSynth(
//                        onSpeak = { text ->
//                            val params = Bundle()
//                            // params.putInt(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "utteranceId") // Optional utterance ID
//                            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, params, null)
//                        },
//                        onStop = { textToSpeech.stop() },
//                        welcomeMessage = msg1,
//                        closingMessage = msg2
//                    )
//                    Button(onClick = {navController.navigate("greeting")}) {
//                        Text("ShowFamily")
//                    }

                   // DisplayFamily(familyMembers,navController)
                   // NavDrawEX(drawerState, navController)
                }

            }
        }
    }
}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LibraryTheme {
        Greeting("Android")
    }
}

@Composable
fun DialogueTest() {
    Box() {
        var openDialog by remember { mutableStateOf(true) }
        val dialogWidth = 200.dp
        val dialogHeight = 50.dp

        if (openDialog) {
            Dialog(onDismissRequest = { openDialog = false }) {
                Box(
                    Modifier
                        .size(dialogWidth, dialogHeight)
                        .background(Color.White)
                ) {
                    Text("Hi Saar")
                }
            }
        }
    }
}

@Preview
@Composable
fun DialogueTestPreview() {
    DialogueTest()
}

@Composable
fun DialogTest2() {
    var openDialog: () -> Unit
    Dialog({ openDialog() }) {
        Text("Hello SirJi")
    }
}

@Preview
@Composable
fun DialogTest2Preview() {
    DialogTest2()
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun VibrateMyPhone(vibratorHelper: VibratorHelper) {
    Button(onClick = { vibratorHelper.vibratePhone() }) {
        Text("Vibrate My Phone")
    }
}

@Composable
fun DispatcherExample() {
    val scope = rememberCoroutineScope()
    var userData by remember { mutableStateOf("Fetching...") }

    Button(onClick = {
        scope.launch { userData = fetchDataFromAPI().toString() }
    }) {
        Text("Fetch User Data")
    }
    Text(userData)
}

suspend fun fetchDataFromAPI() {
    delay(5000)
    "User Data Fetched"
}


@Composable
fun NavDrawEX(drawerState: DrawerState, navController: NavController) {

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text("Drawer Title", modifier = Modifier.padding(16.dp))
                    HorizontalDivider()
                    NavigationDrawerItem(
                        label = { Text("Item1") },
                        selected = false,
                        onClick = { navController.navigate("DisplayFamily") })
                    NavigationDrawerItem(label = { Text("Item2") }, selected = false, onClick = {})
                }
            }
        }, drawerState = drawerState
    ) { }
}

@Composable
fun DoSpeechSynth(
    onSpeak: (String) -> Unit,
    onStop: (String) -> Unit,
    welcomeMessage: String,
    closingMessage: String
) {
    val context = LocalContext.current
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    Button(onClick = {
        CoroutineScope(Dispatchers.IO).launch {
            val result = audioManager.requestAudioFocus(
                AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK).build()
            )
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                onSpeak(welcomeMessage)
                audioManager.abandonAudioFocusRequest(
                    AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                        .build()
                )
                println("Audio Focus Released")
            }
        }
    }) {
        Text("Speak")
    }
    Button(onClick = { onStop(closingMessage) }) {
        Text("Stop")
    }
}

@Composable
fun GetHindiTTSPack(context: Context) {
    Button(onClick = {
        val installIntent = Intent().apply {
            action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
        }
        context.startActivity(installIntent)
    }) {
        Text("Download Hindi TTS")
    }
}

@Composable
fun VertexAITest(data: String){

    var text by remember { mutableStateOf(" Fetching AI Response") }
    val scope = rememberCoroutineScope()
    Button(onClick = {scope.launch{

        text = getAIResponse(data)
    }}) {
        Text("Get Response from AI")
    }

Text(text)
}

suspend fun getAIResponse(prompt: String): String {
    //Vertex AI
    val generativeModel = Firebase.vertexAI.generativeModel("gemini-1.5-flash")

    // Provide a prompt that contains text
    val prompt = prompt

    // To generate text output, call generateContent with the text input
    val response = generativeModel.generateContent(prompt)

    return response.text.toString()
}


@Composable
fun Homie(navController: NavController){
    Button(onClick = {navController.navigate("addUser")}) {
        Text("Go to AddUser")
    }

}

@Composable
fun NavigateToMainActivity3(navController: NavController, context: Context) {
    Button(onClick = {
        // Use Intent to navigate to MainActivity3

        val intent = Intent(context, MainActivity3::class.java)
        context.startActivity(intent)
    }) {
        Text("Go to MainActivity3")
    }
}




