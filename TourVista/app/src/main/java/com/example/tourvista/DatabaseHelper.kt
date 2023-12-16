package com.example.tourvista

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "tourvista.db"
        const val DATABASE_VERSION = 1
        const val TABLE1_USER = "User"
        private const val COLUMN_USER_ID = "Id"
        const val COLUMN_USER_NAME = "Name"
        const val COLUMN_USER_EMAIL = "Email"
        const val COLUMN_USER_PHONE_NUMBER = "PhoneNumber"
        const val COLUMN_USER_PASSWORD = "Password"

        const val TABLE2_AGENT = "Agent"
        private const val COLUMN_AGENT_ID = "Id"
        const val COLUMN_COMPANY_NAME = "CompanyName"
        const val COLUMN_COMPANY_CNIC = "CNIC"
        const val COLUMN_COMPANY_EMAIL = "Email"
        const val COLUMN_COMPANY_PHONE_NUMBER = "PhoneNumber"
        const val COLUMN_COMPANY_PASSWORD = "Password"

        const val CREATE_TABLE1 =
            "CREATE TABLE $TABLE1_USER (" +
                    "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_USER_NAME TEXT NOT NULL," +
                    "$COLUMN_USER_EMAIL TEXT UNIQUE NOT NULL," +
                    "$COLUMN_USER_PHONE_NUMBER TEXT UNIQUE NOT NULL," +
                    "$COLUMN_USER_PASSWORD TEXT NOT NULL" +
                    ")"

        const val CREATE_TABLE2 =
            "CREATE TABLE $TABLE2_AGENT (" +
                    "$COLUMN_AGENT_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_COMPANY_NAME TEXT UNIQUE NOT NULL," +
                    "$COLUMN_COMPANY_CNIC TEXT UNIQUE NOT NULL," +
                    "$COLUMN_COMPANY_EMAIL TEXT UNIQUE NOT NULL," +
                    "$COLUMN_COMPANY_PHONE_NUMBER TEXT UNIQUE NOT NULL," +
                    "$COLUMN_COMPANY_PASSWORD TEXT NOT NULL" +
                    ")"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE1)
        db.execSQL(CREATE_TABLE2)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE1_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE2_AGENT")
        onCreate(db)
    }

    fun registerUser(username: String, email: String, phoneNumber: String, password: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_NAME, username)
            put(COLUMN_USER_EMAIL, email)
            put(COLUMN_USER_PHONE_NUMBER, phoneNumber)
            put(COLUMN_USER_PASSWORD, password)
        }
        db.insert(TABLE1_USER, null, values)
        db.close()
    }

    fun registerAgent(companyName: String, CNIC: String, email: String, phoneNumber: String, password: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_COMPANY_NAME, companyName)
            put(COLUMN_COMPANY_CNIC, CNIC)
            put(COLUMN_COMPANY_EMAIL, email)
            put(COLUMN_COMPANY_PHONE_NUMBER, phoneNumber)
            put(COLUMN_COMPANY_PASSWORD, password)
        }
        db.insert(TABLE2_AGENT, null, values)
        db.close()
    }

    fun loginUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(TABLE1_USER, null, selection, selectionArgs, null, null, null)

        val result = cursor.count > 0

        cursor.close()
        return result
    }

    fun loginAgent(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val selection = "$COLUMN_COMPANY_EMAIL = ? AND $COLUMN_COMPANY_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(TABLE2_AGENT, null, selection, selectionArgs, null, null, null)

        val result = cursor.count > 0

        cursor.close()
        return result
    }
}