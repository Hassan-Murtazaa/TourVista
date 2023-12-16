package com.example.tourvista

import android.text.TextUtils
import android.util.Patterns

object ValidationUtilis{

    fun isTextNotEmpty(text:String): Boolean{
        return !TextUtils.isEmpty(text)
    }

    //Email
    fun isValidEmail(text: String): Boolean{
        return if (TextUtils.isEmpty(text)) false
        else Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    fun emailLengthlimit(email: String): Boolean{
        return email.length <= 28
    }

    //Password
    fun passwordLengthLimit(password: String): Boolean {
        return password.length <= 20
    }

    //Username
    fun isUsernameValid(username: String): Boolean {
        val specialCharacters = setOf<Char>(' ', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '=', '-', '{', '}', '[', ']', '|', '\\', '/', '<', '>', ',', '.', '?', '!')
        return !username.any { it in specialCharacters }
    }

    fun usernameLengthLimit(username: String):Boolean{
        return username.length <= 15
    }

    fun usernameMinimumLengthLimit(username: String):Boolean{
        return if (username.length >= 3) { true }
        else { return false }
    }
    //CNIC
    fun isCNICValid(cnic: String): Boolean {
        return cnic.all { it.isDigit()}
    }

    fun cnicLengthLimit(cnic: String):Boolean{
        return cnic.length <= 13
    }

    fun isCNICLengthValid(cnic:String):Boolean{
        return if (cnic.length == 13) { true }
        else { return false }
    }

    //Phone NUmber
    fun isPhonenumberStatWithZero(newPhoneNumber: String): Boolean {
        return if (newPhoneNumber.isEmpty() || (newPhoneNumber.isNotEmpty() && newPhoneNumber.startsWith("0"))) { true }
        else { return false }
    }

    fun isPhonenumberLengthValid(phonenumber: String):Boolean{
        return if (phonenumber.length == 11) { true }
        else { return false }
    }

    fun isPhonenumberValid(phonenumber: String): Boolean {
        return phonenumber.all { it.isDigit()}
    }

    //Company Name
    fun isCompanynameValid(companyname: String): Boolean {
        val specialCharacters = setOf<Char>('@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '=', '-', '{', '}', '[', ']', '|', '\\', '/', '<', '>', ',', '.', '?', '!')
        return !companyname.any { it in specialCharacters }
    }

    fun companynameLengthLimit(companyname: String):Boolean{
        return companyname.length <= 20
    }

    fun companynameMinimumLengthLimit(companyname: String):Boolean{
        return if (companyname.length >= 3) { true }
        else { return false }
    }

    fun checkSameCharacters(str1: String, str2: String): Boolean {
        return str1 == str2
    }
}