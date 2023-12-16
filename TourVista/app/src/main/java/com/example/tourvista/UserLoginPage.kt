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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class UserLoginPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                UserLoginPageDisplay()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLoginPageDisplay() {

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }

    val annotatedString = buildAnnotatedString {
        append("Sign-In as ")
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append("User")
        pop()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D47A1), Color(0xFF42A5F5)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.light),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(0.dp))
            )
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(0.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Welcome!!",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color.White
            )
            Text(
                text = annotatedString, fontSize = 20.sp, color = Color.White
            )
            BoxWithConstraints {
                OutlinedTextField(
                    modifier = Modifier
                        .size(width = 270.dp, height = 60.dp)
                        .fillMaxWidth(0.8f),
                    value = email,
                    onValueChange = {
                        if (ValidationUtilis.emailLengthlimit(it)) {
                            email = it
                        } else {
                            Toast.makeText(context, "Email length is too large!", Toast.LENGTH_LONG)
                                .show()
                        }
                    },
                    label = { Text("Email", color = Color.White) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
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
            }

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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("If you are Travel Agent then ", fontSize = 13.sp, color = Color.White)
                    TextButton(onClick = {
                        val intent = Intent(context, AgentLoginPage::class.java)
                        context.startActivity(intent)
                    }) {
                        Text("Click Here!!", fontSize = 18.sp, color = Color.White)
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sed),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
                Button(
                    onClick = {
                        if (ValidationUtilis.isTextNotEmpty(email) && ValidationUtilis.isTextNotEmpty(
                                password
                            )
                        ) {
                            if (ValidationUtilis.isValidEmail(email)) {
                                val trimEmail =
                                    email.trim() // Remove leading and trailing spaces from email
                                val trimPassword =
                                    password.trim() // Remove leading and trailing spaces from password

                                val userCollection = Firebase.firestore.collection("users")
                                userCollection.whereEqualTo("Email", trimEmail)
                                    .whereEqualTo("Password", trimPassword).get()
                                    .addOnSuccessListener { querySnapshot ->
                                        if (!querySnapshot.isEmpty) {
                                            val username =
                                                querySnapshot.documents[0].getString("Username")
                                            Toast.makeText(
                                                context, "You are Logged-In!", Toast.LENGTH_SHORT
                                            ).show()

                                            val intent = Intent(context, HomePage::class.java)
                                            intent.putExtra("UserName", username)
                                            intent.putExtra("Email", email)
                                            context.startActivity(intent)
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Incorrect Email or Password!",
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

                                //Second IF
                            } else {
                                Toast.makeText(
                                    context, "Invalid Format Email...!", Toast.LENGTH_SHORT
                                ).show()
                            }
                            ///First if
                        } else {
                            Toast.makeText(context, "All fields Required", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .height(50.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(0.dp))
                        .border(2.dp, Color.White, shape = RoundedCornerShape(26.dp)),
                    colors = ButtonDefaults.buttonColors(Color(0xFF0D47A1))

                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
}