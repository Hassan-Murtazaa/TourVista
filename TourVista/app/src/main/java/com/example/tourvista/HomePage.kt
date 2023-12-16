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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("UserName")
        val email = intent.getStringExtra("Email")
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                HomePageDisplay(username = username ?: "", email = email ?: "")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageDisplay(username: String, email: String) {
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }
    var isDropDownExpanded by remember { mutableStateOf(false) }

    val menuItems = listOf("Edit Profile", "History", "Logout")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hello, ${username.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }}!",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 25.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "What are you looking for?",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }

            Box {
                Image(
                    painter = painterResource(R.drawable.man),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {
                            isDropDownExpanded = true
                        }
                )
                DropDownMenuContent(
                    isDropDownExpanded,
                    onDismiss = { isDropDownExpanded = false }) {
                    menuItems.forEach { menuItem ->
                        DropDownMenuItem(onClick = {
                            when (menuItem) {
                                "Edit Profile" -> {
                                    val intent = Intent(context, UserEditProfilePage::class.java)
                                    intent.putExtra("UserName", username)
                                    intent.putExtra("Email", email)
                                    context.startActivity(intent)
                                }

                                "History" -> {
                                    val intent = Intent(context, UserHistoryPage::class.java)
                                    intent.putExtra("UserName", username)
                                    context.startActivity(intent)
                                }

                                "Logout" -> {
                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }
                            isDropDownExpanded = false
                        }) {
                            Text(text = menuItem)
                        }
                    }
                }
            }
        }

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(CircleShape),
            label = { Text("Search", fontFamily = FontFamily.SansSerif) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        )

        Text(
            text = "Explore tours",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp
        )

        LazyColumn {
            item {
                TourItem(
                    imageId = R.drawable.kashmir,
                    title = "Kashmir",
                    agencyName = "Horizon Adventures",
                    price = "12000",
                    username = username
                )
                Spacer(modifier = Modifier.height(15.dp))
                TourItem(
                    imageId = R.drawable.babusar_top,
                    title = "Babusar Top",
                    agencyName = "ExploreJourney",
                    price = "15000",
                    username = username
                )
                Spacer(modifier = Modifier.height(15.dp))
                TourItem(
                    imageId = R.drawable.saif_ul_maluk_lake,
                    title = "Saif ul Maluk Lake",
                    agencyName = "Summit Seekers",
                    price = "13000",
                    username = username
                )
                Spacer(modifier = Modifier.height(15.dp))
                TourItem(
                    imageId = R.drawable.shangrila_resort_skardu,
                    title = "Shangrila Resort Skardu",
                    agencyName = "Elemental Adventures",
                    price = "13500",
                    username = username
                )
            }
        }
    }
}

@Composable
fun DropDownMenuContent(
    expanded: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (expanded) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            content()
        }
    }
}

@Composable
fun DropDownMenuItem(onClick: () -> Unit, content: @Composable () -> Unit) {
    DropdownMenuItem(
        onClick = onClick,
        text = content
    )
}

@Composable
fun TourItem(imageId: Int, title: String, agencyName: String, price: String, username: String) {
    val context = LocalContext.current
    Row()
    {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                text = title,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp)
                .clickable {
                    val intent = Intent(context, BookingPage::class.java)
                    intent.putExtra("UserName", username)
                    context.startActivity(intent)
                }
        ) {
            Column {
                Text(
                    text = agencyName,
                    modifier = Modifier
                        .padding(8.dp, bottom = 2.dp, top = 10.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 15.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp, top = 4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location Icon",
                        tint = Color.Red,
                        modifier = Modifier.size(13.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = title,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 14.sp
                    )
                }
            }

            val priceText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append("$price Rs.")
                }
                withStyle(style = SpanStyle(color = Color.LightGray)) {
                    append("/person")
                }
            }

            Text(
                text = priceText,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart)
                    .padding(bottom = 10.dp),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 14.sp
            )
        }
    }
}