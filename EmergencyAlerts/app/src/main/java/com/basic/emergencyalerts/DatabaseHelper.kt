package com.basic.emergencyalerts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_NAME TEXT, $COL_PHONE TEXT, $COL_PASSWORD TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(name: String, phone: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_PHONE, phone)
        contentValues.put(COL_PASSWORD, password)
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result
    }

    fun loginUser(phone: String, password: String): Boolean {
        val db = this.readableDatabase
        val selection = "$COL_PHONE = ? AND $COL_PASSWORD = ?"
        val selectionArgs = arrayOf(phone, password)
        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val result = cursor.count > 0
        cursor.close()
        db.close()
        return result
    }
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase"
        private const val TABLE_NAME = "users"
        private const val COL_NAME = "name"
        private const val COL_PHONE = "phone"
        private const val COL_PASSWORD = "password"
    }
}