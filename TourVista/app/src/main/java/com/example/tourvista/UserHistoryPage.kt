package com.example.tourvista

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class UserHistoryPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("UserName")
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                HistoryPage(username = username ?: "")
            }
        }
    }
}

data class HistoryItem(
    val title: String,
    val description: String,
)

val historyItems = listOf(
    HistoryItem("Ancient Civilization", "Description of ancient civilization"),
    HistoryItem("Middle Ages", "Description of middle ages"),
    HistoryItem("Renaissance", "Description of renaissance"),
    HistoryItem("Renaissance", "Description of renaissance"),
)

@Composable
fun HistoryPage(username: String) {

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.history),
                contentDescription = "History related image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = "Your History: ",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.Start)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(historyItems) { historyItem ->
                    HistoryItem(historyItem)
                }
            }
        }
        Box(modifier = Modifier
            .padding(10.dp)
            .size(45.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .clickable {
                val intent = Intent(context, HomePage::class.java)
                intent.putExtra("UserName", username)
                context.startActivity(intent)
            }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back Arrow",
                tint = Color.Black,
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.TopStart)
            )
        }
    }
}

@Composable
fun HistoryItem(historyItem: HistoryItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(19.dp)
            .height(120.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF64B5F6), Color(0xFF2196F3)
                    ), // Example bluish gradient colors
                    startY = 0f, endY = 100f
                ), shape = RoundedCornerShape(8.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray, shape = RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.history),
                contentDescription = "History related image",
                modifier = Modifier
                    .fillMaxSize() // Use fillMaxSize to make the image fit entirely within the box
                    .padding(0.dp) // Set padding to 0 to eliminate any extra space
                    .clip(RoundedCornerShape(8.dp)), // Clip the image to the shape of the box
                contentScale = ContentScale.Crop // Use ContentScale.Crop to fit the entire image within the box
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = historyItem.title,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = historyItem.description,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                color = Color.White,
            )
        }
    }
}