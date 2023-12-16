package com.example.tourvista

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class UserSignUpPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                UserSignUpPageDisplay()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSignUpPageDisplay() {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D47A1), Color(0xFF42A5F5)
                    )
                )
            ), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.hto),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shadow(
                        8.dp, RoundedCornerShape(8.dp)
                    )
            )
            Text(
                text = "Sign-Up as User",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White
            )
            OutlinedTextField(
                modifier = Modifier
                    .size(width = 270.dp, height = 60.dp)
                    .fillMaxWidth(0.8f),
                value = username,
                onValueChange = {
                    if (ValidationUtilis.isUsernameValid(it)) {
                        if (ValidationUtilis.usernameLengthLimit(it)) {
                            username = it
                        } else {
                            Toast.makeText(
                                context,
                                "Username Length should be within 15 Words!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Username should not contain Special Characters!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                label = {
                    Text(
                        "Username",
                        color = Color.White,
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(23.dp),
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .size(width = 270.dp, height = 60.dp)
                    .fillMaxWidth(0.8f),
                value = email,
                onValueChange = {
                    if (ValidationUtilis.emailLengthlimit(it)) {
                        email = it
                    } else {
                        Toast.makeText(context, "Email length is too large!", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                label = { Text("Email", color = Color.White) },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(23.dp),
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .size(width = 270.dp, height = 60.dp)
                    .fillMaxWidth(0.8f),
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= 1 || ValidationUtilis.isPhonenumberStatWithZero(it)) {
                        phoneNumber = it
                    } else {
                        Toast.makeText(
                            context, "Phone Number should start with 0!", Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                label = { Text("Phone Number", color = Color.White) },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(23.dp),
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .size(width = 270.dp, height = 60.dp)
                    .fillMaxWidth(0.8f),
                value = password,
                onValueChange = {
                    if (ValidationUtilis.passwordLengthLimit(it)) {
                        password = it
                    } else {
                        Toast.makeText(context, "Password length is too large!", Toast.LENGTH_LONG)
                            .show()
                    }
                },

                label = { Text("Password", color = Color.White) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Default.Info else Icons.Default.Info,
                            contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                            tint = Color.White
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )
            Button(
                onClick = {
                    if (ValidationUtilis.isTextNotEmpty(username) && ValidationUtilis.isTextNotEmpty(
                            email
                        ) && ValidationUtilis.isTextNotEmpty(phoneNumber) && ValidationUtilis.isTextNotEmpty(
                            password
                        )
                    ) {
                        if (ValidationUtilis.usernameMinimumLengthLimit(username)) {
                            if (ValidationUtilis.isValidEmail(email)) {
                                if (ValidationUtilis.isPhonenumberLengthValid(phoneNumber) && ValidationUtilis.isPhonenumberValid(
                                        phoneNumber
                                    )
                                ) {
                                    val db = Firebase.firestore

                                    db.collection("users").whereEqualTo("Email", email).get()
                                        .addOnSuccessListener { querySnapshot ->
                                            if (querySnapshot.isEmpty) {
                                                // Email is unique in the "users" collection
                                                db.collection("users")
                                                    .whereEqualTo("PhoneNumber", phoneNumber).get()
                                                    .addOnSuccessListener { phoneNumberQuerySnapshot ->
                                                        if (phoneNumberQuerySnapshot.isEmpty) {
                                                            // Phone number is also unique in the "users" collection
                                                            db.collection("agents")
                                                                .whereEqualTo("Email", email).get()
                                                                .addOnSuccessListener { agentsEmailSnapshot ->
                                                                    if (agentsEmailSnapshot.isEmpty) {
                                                                        // Email is unique in the "agents" collection
                                                                        db.collection("agents")
                                                                            .whereEqualTo(
                                                                                "PhoneNumber",
                                                                                phoneNumber
                                                                            ).get()
                                                                            .addOnSuccessListener { agentsPhoneNumberSnapshot ->
                                                                                if (agentsPhoneNumberSnapshot.isEmpty) {
                                                                                    // Phone number is also unique in the "agents" collection
                                                                                    val intent =
                                                                                        Intent(
                                                                                            context,
                                                                                            OTPGenerationPage::class.java
                                                                                        )
                                                                                    intent.putExtra(
                                                                                        "UserName",
                                                                                        username
                                                                                    )
                                                                                    intent.putExtra(
                                                                                        "Email",
                                                                                        email
                                                                                    )
                                                                                    intent.putExtra(
                                                                                        "PhoneNumber",
                                                                                        phoneNumber
                                                                                    )
                                                                                    intent.putExtra(
                                                                                        "Password",
                                                                                        password
                                                                                    )
                                                                                    context.startActivity(
                                                                                        intent
                                                                                    )
                                                                                } else {
                                                                                    //Phone number already exists in agents collection
                                                                                    Toast.makeText(
                                                                                        context,
                                                                                        "Phone number already exists",
                                                                                        Toast.LENGTH_SHORT
                                                                                    ).show()
                                                                                }
                                                                            }.addOnFailureListener {
                                                                                Toast.makeText(
                                                                                    context,
                                                                                    "Server not Responding at the moment!",
                                                                                    Toast.LENGTH_SHORT
                                                                                ).show()
                                                                            }
                                                                    } else {
                                                                        //Email already exists in agents collection
                                                                        Toast.makeText(
                                                                            context,
                                                                            "Email already exists",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                    }
                                                                }.addOnFailureListener {
                                                                    Toast.makeText(
                                                                        context,
                                                                        "Server not Responding at the moment!",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                        } else {
                                                            //Phone number already exists in users collection
                                                            Toast.makeText(
                                                                context,
                                                                "Phone number already exists",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }.addOnFailureListener {
                                                        Toast.makeText(
                                                            context,
                                                            "Server not Responding at the moment!",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                            } else {
                                                //Email already exists in users collection
                                                Toast.makeText(
                                                    context,
                                                    "Email already exists",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }.addOnFailureListener {
                                            Toast.makeText(
                                                context,
                                                "Server not Responding at the moment!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                                //Fourth IF
                                else {
                                    Toast.makeText(
                                        context,
                                        "Phone Number should contain 11 Numbers!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                //Third IF
                            } else {
                                Toast.makeText(
                                    context, "Invalid Format Email...!", Toast.LENGTH_SHORT
                                ).show()
                            }
                            //Second IF
                        } else {
                            Toast.makeText(
                                context,
                                "Username length should be Greater than 3!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        ///First if
                    } else {
                        Toast.makeText(context, "All fields Required", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF1565C0))
                    .border(2.dp, Color.White, shape = RoundedCornerShape(26.dp)),
                colors = ButtonDefaults.buttonColors(Color(0xFF0D47A1)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "SignUp",
                    color = Color.White,
                    fontSize = 18.sp,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("If you are Travel Agent then ", fontSize = 15.sp, color = Color.White)
                Spacer(modifier = Modifier.width(4.dp)) // Add some space between the texts
                TextButton(onClick = {
                    val intent = Intent(context, AgentSignUpPage::class.java)
                    context.startActivity(intent)
                }) {
                    Text(
                        "Click Here!!",
                        fontSize = 15.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}