package com.example.taskmenager.Model

import com.example.taskmenager.DB.DBConnection

class ModelTask(dbConnection: DBConnection)
{
    private val dbConnection: DBConnection = dbConnection

    fun insertData(task: Task)
    {
        dbConnection.insertData(task)
    }
    fun getAllTasks(): MutableList<Task>
    {
        return dbConnection.getAllTasks()
    }
    fun deleteTask(task: Task)
    {
        dbConnection.deleteTask(task)
    }
    fun selectTask(id: Int): Task?
    {
        return dbConnection.selectTask(id)
    }
    fun editTask(task: Task)
    {
        dbConnection.editTask(task)
    }
}