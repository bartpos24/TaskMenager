package com.example.taskmenager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import com.example.taskmenager.DB.DBConnection
import com.example.taskmenager.DB.TaskListFragment
import com.example.taskmenager.Model.ModelTask
import android.widget.Toast
import com.example.taskmenager.Model.Task
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.activity_add_task.topic_plainText



class AddTaskActivity : AppCompatActivity() {

    private val modelTask = ModelTask(DBConnection(this))
    private var tasklistfragment: TaskListFragment =
        TaskListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        btnEditTask.setOnClickListener { handleClick() }

    }

    fun handleClick()
    {
        val name = topic_plainText.text.toString()
        if (name.isEmpty())
        {
            Toast.makeText(applicationContext, "Name is required", Toast.LENGTH_SHORT).show()
        }
        val description = taskDescription.text.toString()
        if (description.isEmpty())
        {
            Toast.makeText(applicationContext, "Description is required", Toast.LENGTH_SHORT).show()
        }
        val chosenRadioId = radioPriority.checkedRadioButtonId
        if (chosenRadioId == -1)
        {
            Toast.makeText(applicationContext, "Choose priority", Toast.LENGTH_SHORT).show()
        }
        val radio: RadioButton = findViewById(chosenRadioId)
        val priority: String = radio.text.toString()
        val task = Task(name,description,priority)
        modelTask.insertData(task)
        Toast.makeText(applicationContext, "Task $name has been added", Toast.LENGTH_SHORT).show()
        tasklistfragment.ClearListView()

        val tasks = modelTask.getAllTasks()

        tasklistfragment.AddToListView(tasks)
        finish()
    }
}
