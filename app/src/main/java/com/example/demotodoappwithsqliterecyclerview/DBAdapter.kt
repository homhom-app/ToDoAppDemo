package com.example.demotodoappwithsqliterecyclerview

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class DBAdapter(context: Context) {
    private val db: SQLiteDatabase
    private val userDB: DBOpenHelper = DBOpenHelper(context)
    init {
        db = userDB.writableDatabase
    }

    fun addRecord(task: String?, priority:Int?, remarks:String?) {
        val values = ContentValues()
        values.put("task", task)
        values.put("priority", priority)
        values.put("remarks", remarks)
        db.insertOrThrow(DBOpenHelper.TABLE_NAME, null, values)
        Log.d("DB Add","set task = ${values.get("task")}, priority = ${values.get("priority")}, remarks = ${values.get("remarks")}")
    }

    private fun getId(cursor: Cursor) : String {
        return cursor.getInt(cursor.getColumnIndex("_id")).toString()
    }

    fun updateProgress(cursor: Cursor) {

        val id = cursor.getInt(cursor.getColumnIndex("_id"))
        Log.d("DB Update","cursor position = ${cursor.position}, _id = $id")
        var progress = cursor.getInt(cursor.getColumnIndex("progress"))

        progress = if (progress == 0) 1
        else 0
        Log.d("DB Update","cursor position = ${cursor.position}")
        val values = ContentValues()
        values.put("progress", progress)
        db.update(DBOpenHelper.TABLE_NAME,values, "_id = ?", arrayOf(getId(cursor)))
    }

    fun deleteRecord(cursor: Cursor) {
        db.delete(DBOpenHelper.TABLE_NAME,"_id = ?", arrayOf(getId(cursor)))
    }

    fun getRecords() : Cursor {
        val cursor = db.query(DBOpenHelper.TABLE_NAME,null,null,null,null,null,"priority desc",null)
        cursor.moveToFirst()
        return cursor
    }

}