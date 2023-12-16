package com.example.tourvista

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AgentHomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                AgentHomePageDisplay()
            }
        }
    }
}

data class AgentRecord(
    val description: String,
    val price: String,
    val date: String,
    val itinerary: String,
    val includes: List<String>,
    val destination: String
)

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentHomePageDisplay() {
    var date by remember { mutableStateOf("") }

    val checkboxOptions = listOf("Lunch", "Dinner", "Bonfire", "Qawali")
    var checkedState = remember { mutableStateListOf(false, false, false, false) }

    var agentRecord by remember {
        mutableStateOf(
            AgentRecord(
                "", "", "", "", emptyList(), ""
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Welcome message and Logout icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Welcome, Agent!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = CircleShape)
                    .clickable {
                        // Handle logout action
                        // You can implement the logout logic here
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Logout Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(45.dp)
                        .align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Add Records",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        PickImageFromGallery()

        Spacer(modifier = Modifier.height(16.dp))

        var price by remember { mutableStateOf("") }
        var isPriceValid by remember { mutableStateOf(true) }

        var Itinerary by remember { mutableStateOf("") }
        var destination by remember { mutableStateOf("") }

        TextField(
            value = agentRecord.description,
            onValueChange = { agentRecord = agentRecord.copy(description = it) },
            label = { Text("Description", color = Color.Black) },
            leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = price,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    price = it
                    isPriceValid = true
                } else {
                    isPriceValid = false
                }
            },
            label = {
                Text(
                    "Price",
                    color = if (isPriceValid) Color.Black else MaterialTheme.colorScheme.error
                )
            },
            leadingIcon = { Icon(Icons.Default.PriceChange, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, capitalization = KeyboardCapitalization.None
            )
        )

        if (!isPriceValid) {
            Text(
                "Invalid price. Please enter digits only.",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = agentRecord.date,
                onValueChange = { agentRecord = agentRecord.copy(date = it) },
                label = { Text("Date", color = Color.Black) },
                leadingIcon = { Icon(Icons.Default.Update, contentDescription = null) },
            )
            date = MyContent()
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = Itinerary,
            onValueChange = { Itinerary = it },
            label = { Text("Itinerary", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.CalendarToday, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Package Includes",
                style = MaterialTheme.typography.headlineSmall,
            )

            Icon(
                Icons.Filled.CalendarToday, contentDescription = null,
            )
        }

        checkboxOptions.forEachIndexed { index, label ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp)
            ) {
                Checkbox(
                    checked = agentRecord.includes.contains(label),
                    onCheckedChange = {
                        val updatedIncludes =
                            if (it) agentRecord.includes + label else agentRecord.includes - label
                        agentRecord = agentRecord.copy(includes = updatedIncludes)
                    }
                )
                Text(
                    text = label, modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = agentRecord.destination,
            onValueChange = { agentRecord = agentRecord.copy(destination = it) },
            label = { Text("Tour Destination", color = Color.Black) },
            leadingIcon = { Icon(Icons.Filled.DirectionsBus, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle click */ },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Add Record", color = Color.White
            )
        }
    }
}

@Composable
fun PickImageFromGallery() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }

        bitmap.value?.let { btm ->
            Image(
                bitmap = btm.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(20.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Button(
        onClick = { launcher.launch("image/*") },
        modifier = Modifier.padding(start = 10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(20, 118, 209))
    ) {
        Text(
            text = "Pick Image", color = Color.White
        )
    }
}