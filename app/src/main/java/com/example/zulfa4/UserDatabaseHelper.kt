package com.example.zulfa4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabaseHelper (context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user_db"
        private const val DATABASE_VERSION = 1
        private  const val TABLE_USER = "user"
        private const val COLUMN_ID = "user"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = """
            CREATE TABLE $TABLE_USER (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL
                )
            """.trimIndent()
        db.execSQL(createUserTable)

        // Sample data
        val sampleInsert = """
                INSERT INTO $TABLE_USER ($COLUMN_USERNAME, $COLUMN_PASSWORD)
                VALUES ('admin', '12345'), ('user', 'password')
            """
        db.execSQL(sampleInsert)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF TABLE_$TABLE_USER")
        onCreate(db)
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USER WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }
}