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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class BookingPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("UserName")
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                BookingPageDisplay(username = username ?: "")
            }
        }
    }
}

@Composable
fun BookingPageDisplay(username: String) {
    var focusedButton by remember { mutableStateOf("details") }
    val colorDetails = if (focusedButton == "details") Color.LightGray else Color.White
    val colorItinerary = if (focusedButton == "itinerary") Color.LightGray else Color.White
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kashmir),
                contentDescription = "Kashmir",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .padding(26.dp)
                    .size(50.dp)
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
                    .clickable {
                        val intent = Intent(context, HomePage::class.java)
                        intent.putExtra("UserName", username)
                        context.startActivity(intent)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back Arrow",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .padding(26.dp)
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
                    .align(Alignment.BottomCenter)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(0.6f)
                        ) {
                            Column {
                                Text(
                                    text = "Horizon Adventures",
                                    modifier = Modifier
                                        .padding(16.dp, top = 10.dp, bottom = 4.dp),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(16.dp, top = 0.dp),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Location Icon",
                                        tint = Color.Red,
                                        modifier = Modifier.size(13.dp)
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = "Kashmir",
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.SansSerif,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(32.dp))

                        Box(
                            modifier = Modifier
                                .weight(0.4f)
                        ) {
                            Column {
                                Text(
                                    text = "12000 R.s/person",
                                    modifier = Modifier
                                        .padding(16.dp, top = 10.dp),
                                    color = Color(103, 52, 235),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 15.sp,
                                )
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Participants:",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        var participants by remember { mutableIntStateOf(1) }

                        Button(
                            onClick = {
                                participants = if (participants > 1) participants - 1 else 1
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF42A5F5)),
                            content = { Text(text = "-", color = Color.Black, fontSize = 14.sp) }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = participants.toString(),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { participants++ },
                            colors = ButtonDefaults.buttonColors(Color(0xFF42A5F5)),
                            content = { Text(text = "+", color = Color.Black, fontSize = 14.sp) }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 0.dp)
                            .height(32.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Button(
                            onClick = {
                                focusedButton = "details"
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(colorDetails),
                            content = {
                                Text(
                                    text = "Details",
                                    color = if (focusedButton == "details") Color.Black else Color.LightGray,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 13.sp
                                )
                            }
                        )

                        Button(
                            onClick = {
                                focusedButton = "itinerary"
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(colorItinerary),
                            content = {
                                Text(
                                    text = "Itinerary",
                                    color = if (focusedButton == "itinerary") Color.Black else Color.LightGray,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 13.sp
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .fillMaxHeight(0.6f)
                    ) {
                        if (focusedButton == "details") {
                            DetailsScreen()
                        } else {
                            ItineraryScreen()
                        }
                    }
                }

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(15.dp)
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(Color(0xFF0D47A1)),
                    content = {
                        Text(
                            text = "Book Now",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun DetailsScreen() {
    LazyColumn {
        item {
            Text(
                text = "This 4-day package takes you on an unforgettable journey to Kashmir. You will have a chance to see the breathtaking landscapes of the picturesque valleys, explore the rich cultural heritage of the region, and indulge in the warmth of Kashmiri hospitality. Immerse yourself in the beauty of iconic landmarks, savor local cuisine, and create lasting memories amidst the serene surroundings of this enchanting destination.\n",
                fontSize = 12.sp,
                textAlign = TextAlign.Justify
            )
            val packageIncludes = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Package Includes:")
                }
                append(
                    "\n" +
                            "1. Transportation\n" +
                            "2. Accommodations\n" +
                            "3. Meals\n" +
                            "4. Musical Night | BBQ"
                )
            }
            Text(
                text = packageIncludes,
                fontSize = 12.sp,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
fun ItineraryScreen() {
    LazyColumn {
        item {
            val itinerary = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Day 1: Arrival in Srinagar\n")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Day 2: Gulmarg Excursion\n")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Day 3: Pahalgam Day Trip\n")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Day 4: Departure")
                }
            }

            Text(
                text = itinerary,
                fontSize = 12.sp,
                textAlign = TextAlign.Justify
            )
        }
    }
}
