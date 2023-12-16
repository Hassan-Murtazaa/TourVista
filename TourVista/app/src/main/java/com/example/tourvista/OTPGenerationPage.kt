package com.example.tourvista

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.util.concurrent.TimeUnit

class OTPGenerationPage : ComponentActivity() {
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var phoneNumber: String
    private lateinit var password: String
    private lateinit var companyName: String
    private lateinit var cnic: String

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        username = intent.getStringExtra("UserName") ?: ""
        email = intent.getStringExtra("Email") ?: ""
        phoneNumber = intent.getStringExtra("PhoneNumber") ?: ""
        password = intent.getStringExtra("Password") ?: ""
        companyName = intent.getStringExtra("Company_Name") ?: ""
        cnic = intent.getStringExtra("CNIC") ?: ""

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                OTPPageDisplay(phoneNumber = phoneNumber ?: "", this@OTPGenerationPage)
            }
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(this@OTPGenerationPage, "Invalid Request", Toast.LENGTH_SHORT)
                        .show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(this@OTPGenerationPage, "SMS Quota Exceeded", Toast.LENGTH_SHORT)
                        .show()
                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                    Toast.makeText(
                        this@OTPGenerationPage,
                        "reCAPTCHA verification attempted with null Activity",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(ContentValues.TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                navigateToAnotherActivity()
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(this, "Invalid Code", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToAnotherActivity() {
        if (companyName.isNotBlank() && cnic.isNotBlank()) {
            val agent = hashMapOf(
                "CompanyName" to companyName,
                "CNIC" to cnic,
                "Email" to email,
                "PhoneNumber" to phoneNumber,
                "Password" to password,
            )
            val agentCollection = Firebase.firestore.collection("agents")
            agentCollection.add(agent).addOnSuccessListener {
                    Toast.makeText(
                        this, "Successfully Registered", Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, AgentHomePage::class.java)
                    intent.putExtra("Company_Name", companyName)
                    intent.putExtra("CNIC", cnic)
                    this.startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(
                        this, "Server not Responding at the moment!", Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            val user = hashMapOf(
                "Username" to username,
                "Email" to email,
                "PhoneNumber" to phoneNumber,
                "Password" to password,
                "Gender" to null
            )
            val userCollection = Firebase.firestore.collection("users")
            userCollection.add(user).addOnSuccessListener {
                    Toast.makeText(
                        this, "Successfully Registered", Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, HomePage::class.java)
                    intent.putExtra("UserName", username)
                    intent.putExtra("Email", email)
                    this.startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(
                        this, "Server not Responding at the moment!", Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    fun startPhoneNumberVerification(phoneNumber: String) {
        val options =
            PhoneAuthOptions.newBuilder(auth).setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun resendVerificationCode(phoneNumber: String) {
        val optionsBuilder =
            PhoneAuthOptions.newBuilder(auth).setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // (optional) Activity for callback binding
                // If no activity is passed, reCAPTCHA verification can not be used.
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
        optionsBuilder.setForceResendingToken(resendToken)
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    fun verifyPhoneNumberWithCode(phoneNumber: String, verificationCode: String) {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, verificationCode)
        signInWithPhoneAuthCredential(credential)
    }
}

@Composable
fun OTPPageDisplay(phoneNumber: String, otp: OTPGenerationPage) {
    val formattedPhoneNumber = formatPhoneNumberForFirebase(phoneNumber)
    otp.startPhoneNumberVerification(formattedPhoneNumber)

    val focusRequesters = remember {
        List(6) { FocusRequester() }
    }

    val context = LocalContext.current
    val otpState = remember { mutableStateListOf("", "", "", "", "", "") }
    var concatenatedOtp by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
    ) {
        Text(
            text = "Please enter the security code received via SMS on your registered mobile phone",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until 6) {
                BasicTextField(
                    value = otpState[i],
                    onValueChange = {
                        if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                            otpState[i] = it
                            if (it.length == 1 && i < 5) {
                                focusRequesters[i + 1].requestFocus()
                            }
                        }
                        concatenatedOtp = otpState.joinToString(separator = "")
                    },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(
                        fontSize = 20.sp, textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .height(35.dp)
                        .focusRequester(focusRequesters[i])
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text("Didn't receive OTP?", fontSize = 13.sp)
            TextButton(
                onClick = {
                    val formattedPhoneNumber = formatPhoneNumberForFirebase(phoneNumber)
                    otp.resendVerificationCode(formattedPhoneNumber)
                },
            ) {
                Text(
                    text = "Resend OTP",
                    textDecoration = TextDecoration.Underline,
                    fontSize = 16.sp,
                    color = Color.Blue,
                )
            }
        }

        Button(
            onClick = {
                if (concatenatedOtp.isNotEmpty()) {
                    otp.verifyPhoneNumberWithCode(phoneNumber, concatenatedOtp)
                } else {
                    Toast.makeText(
                        context, "Please enter verification code", Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 30.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF0D47A1)),
        ) {
            Text(text = "Verify OTP")
        }
    }
}

fun formatPhoneNumberForFirebase(phoneNumber: String): String {
    // Check if the phone number starts with a leading zero and remove it
    val cleanedPhoneNumber = if (phoneNumber.startsWith("0")) {
        phoneNumber.substring(1)
    } else {
        phoneNumber
    }

    // Add the country code for Pakistan (+92) to the formatted number
    return "+92$cleanedPhoneNumber"
}