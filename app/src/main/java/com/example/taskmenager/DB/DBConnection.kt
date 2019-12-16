package com.example.taskmenager.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.taskmenager.Model.PrioritySort
import com.example.taskmenager.Model.Task
import java.text.SimpleDateFormat
import java.util.*

class DBConnection(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object
    {
        internal val DATABASE_NAME = "tasks.db"
        internal val DATABASE_VERSION = 1
        internal const val TABLE_NAME = "tasks"
        internal const val COL_ID = "id"
        internal const val COL_TOPIC = "topic"
        internal const val COL_PRIORITY = "priority"
        internal const val COL_STATUS = "status"
        internal const val COL_DATA = "data"
        internal const val COL_DESCRIPTION = "description"
    }



    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE $TABLE_NAME( " +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_TOPIC TEXT," +
                "$COL_PRIORITY TEXT," +
                "$COL_STATUS TEXT," +
                "$COL_DATA TEXT," +
                "$COL_DESCRIPTION TEXT)"
                )
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS $TABLE_NAME"
        db!!.execSQL(query)
        onCreate(db)
    }

    fun insertData(task: Task)
    {
        val db = this.writableDatabase
        val formateDate = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val strDate = formateDate.format(task.date)
        val values = ContentValues().apply {
            put(COL_TOPIC, task.topic)
            put(COL_PRIORITY, task.priority)
            put(COL_STATUS, task.status)
            put(COL_DATA, strDate)
            put(COL_DESCRIPTION, task.description)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()

    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    fun getAllTasks(): MutableList<Task>
    {
        val db = this.writableDatabase

        val query = "SELECT *FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        val taskList = mutableListOf<Task>()
        with(cursor)
        {
            while (moveToNext())
            {

                val id = Integer.parseInt(cursor.getString(0))
                val topic = (cursor.getString(1))
                val priority = (cursor.getString(2))
                val status = (cursor.getString(3))
                val date = (cursor.getString(4))
                val description = (cursor.getString(5))

                val dateDate = SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date)
                if (dateDate != null)
                {
                    val task = Task(topic,description,priority,dateDate as Date, status, id)
                    taskList.add(task)
                }

            }
        }
        taskList.sortWith(PrioritySort)
        cursor.close()
        db.close()

        return taskList
    }
    fun deleteTask(task: Task)
    {
        val db = this.writableDatabase
        db!!.execSQL("DELETE FROM $TABLE_NAME WHERE $COL_ID = \"${task.id}\" ")
        db.close()
    }
    fun selectTask(id: Int): Task?
    {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_ID = $id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = Integer.parseInt(cursor.getString(0))
        val topic = (cursor.getString(1))
        val priority = (cursor.getString(2))
        val status = (cursor.getString(3))
        val date = (cursor.getString(4))
        val description = (cursor.getString(5))

        val dateDate = SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date)
        if (dateDate != null)
        {
            cursor.close()
            db.close()
            return Task(topic, description, priority, dateDate as Date, status, id)
        }
        else
        {
            cursor.close()
            db.close()
            return null
        }

    }
    fun editTask(task: Task)
    {
        val db = writableDatabase
        val args = listOf<String>(task.id.toString()).toTypedArray()
        val formatDate = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val dateDate = formatDate.format(task.date)
        val values = ContentValues().apply {
            put(COL_TOPIC, task.topic)
            put(COL_DATA, dateDate)
            put(COL_DESCRIPTION, task.description)
            put(COL_PRIORITY, task.priority)
            put(COL_STATUS, task.status)
        }
        db.update(TABLE_NAME,values, "$COL_ID=?", args)

    }
}