package com.example.tourvista

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourvista.ui.theme.TourVistaTheme

class UserEditProfilePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val usernamee = intent.getStringExtra("UserName")
        val email = intent.getStringExtra("Email")
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                UserEditProfilePageDisplay(usernamee = usernamee ?: "")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEditProfilePageDisplay(usernamee: String) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility1 by remember { mutableStateOf(false) }
    var passwordVisibility2 by remember { mutableStateOf(false) }

    var checkNewPassword by remember { mutableStateOf(false) }
    var checkConfirmPassword by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .size(45.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .clickable {
                val intent = Intent(context, HomePage::class.java)
                intent.putExtra("UserName", usernamee)
                context.startActivity(intent)
            }
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Back Arrow",
            tint = Color.Black,
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.TopStart)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Edit Profile",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {
                if(ValidationUtilis.isUsernameValid(it)){
                    if(ValidationUtilis.usernameLengthLimit(it)) {
                        username = it
                    } else{
                        Toast.makeText(context, "Username Length should be within 20 Words!", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, "Username should not contain Special Character!", Toast.LENGTH_SHORT).show()
                }
            },
            label = { Text("Username") },
            leadingIcon = {
                Icon(
                    Icons.Default.Person, contentDescription = null, modifier = Modifier
                        .size(23.dp)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .size(width = 270.dp, height = 60.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                if (ValidationUtilis.emailLengthlimit(it)) {
                    email = it
                } else {
                    Toast.makeText(context, "Email length is too large !", Toast.LENGTH_SHORT).show()
                }
            },
            label = { Text("Email") },
            leadingIcon = {
                Icon(
                    Icons.Default.Email, contentDescription = null, modifier = Modifier
                        .size(23.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .size(width = 270.dp, height = 60.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                if (it.length <= 1 || ValidationUtilis.isPhonenumberStatWithZero(it)) {
                    phoneNumber = it
                } else {
                    Toast.makeText(context, "Phone Number should start with 0!", Toast.LENGTH_SHORT).show()
                }
            },
            label = { Text("Phone number") },
            leadingIcon = {
                Icon(
                    Icons.Default.Phone, contentDescription = null, modifier = Modifier
                        .size(23.dp)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier
                .fillMaxWidth()
                .size(width = 270.dp, height = 60.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                if (ValidationUtilis.passwordLengthLimit(it)) {
                    newPassword = it
                    checkNewPassword = !newPassword.isNullOrEmpty()
                } else {
                    Toast.makeText(context, "Password length is too large !", Toast.LENGTH_LONG).show()
                }
            },
            label = { Text("New Password") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(23.dp))
            },

            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            singleLine = true,

            trailingIcon = {
                IconButton(onClick = { passwordVisibility1 = !passwordVisibility1 }) {
                    Icon(
                        imageVector = if (passwordVisibility1) Icons.Default.Info else Icons.Default.Info,
                        contentDescription = if (passwordVisibility1) "Hide password" else "Show password",
                        tint = Color.Black
                    )
                }
            },
            visualTransformation = if (passwordVisibility1) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().size(width = 270.dp, height = 60.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                if (ValidationUtilis.passwordLengthLimit(it)) {
                    confirmPassword = it
                    checkConfirmPassword = !confirmPassword.isNullOrEmpty()
                } else {
                    Toast.makeText(context, "Password length is too large !", Toast.LENGTH_LONG).show()
                }
            },
            label = { Text("Confirm password") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(23.dp))
            },

            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            singleLine = true,

            trailingIcon = {
                IconButton(onClick = { passwordVisibility2 = !passwordVisibility2 }) {
                    Icon(
                        imageVector = if (passwordVisibility2) Icons.Default.Info else Icons.Default.Info,
                        contentDescription = if (passwordVisibility2) "Hide password" else "Show password",
                        tint = Color.Black
                    )
                }
            },
            visualTransformation = if (passwordVisibility2) VisualTransformation.None else PasswordVisualTransformation(),

            modifier = Modifier.fillMaxWidth().size(width = 270.dp, height = 60.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Gender",
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp)
        )

        var selectedOption by remember { mutableStateOf<String?>(null) }

        RadioButtonSample(selectedOption = selectedOption) {
            selectedOption = it
        }

        Button(
            onClick = {
                if (ValidationUtilis.isTextNotEmpty(username) && ValidationUtilis.isTextNotEmpty(email) && ValidationUtilis.isTextNotEmpty(phoneNumber)) {
                    if (ValidationUtilis.usernameMinimumLengthLimit(username)) {
                        if (ValidationUtilis.isValidEmail(email)) {
                            if (ValidationUtilis.isPhonenumberLengthValid(phoneNumber) && ValidationUtilis.isPhonenumberValid(phoneNumber)) {
                                // Validation for first three fields passed
                                if (checkNewPassword || checkConfirmPassword) {
                                    if (ValidationUtilis.checkSameCharacters(newPassword, confirmPassword)) {
                                        // Passwords are valid, call the backend function for data posting
                                        //callBackendFunctionForDataPosting()
                                    } else {
                                        Toast.makeText(context, "New Password and Confirm Password are not Same!", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    // Call the backend function for data posting without passwords
                                    //callBackendFunctionForDataPosting()
                                }
                            } else {
                                Toast.makeText(context, "Phone Number should contain 13 Numbers!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Invalid Format Email...!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Username should be Greater than 3!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please input all fields", Toast.LENGTH_SHORT).show()
                }
            },



            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF1565C0)),
            colors = ButtonDefaults.buttonColors(Color(0xFF0D47A1)),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Update Profile",
                color = Color.White,
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
fun RadioButtonSample(selectedOption: String?, onOptionSelected: (String?) -> Unit) {
    val radioOptions = listOf("Male", "Female")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .weight(1f)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall.merge(),
                    modifier = Modifier.padding(top = 13.dp, start = 8.dp),
                    color = Color.Black,
                )
            }
        }
    }
}