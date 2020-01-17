package com.example.demotodoappwithsqliterecyclerview

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DATABASE_VERSION = 1

class DBOpenHelper (context: Context) : SQLiteOpenHelper(context, "todo.db", null, DATABASE_VERSION) {
    companion object {
        const val TABLE_NAME = "todo"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_NAME (_id integer primary key autoincrement,task text, priority integer, remarks text, progress integer);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}