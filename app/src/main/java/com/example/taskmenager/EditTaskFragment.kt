package com.example.taskmenager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taskmenager.DB.DBConnection
import com.example.taskmenager.Model.ModelTask
import com.example.taskmenager.Model.Priority
import com.example.taskmenager.Model.Status
import com.example.taskmenager.Model.Task


import kotlinx.android.synthetic.main.edit_task_fragment.*
import kotlinx.android.synthetic.main.edit_task_fragment.radioPriority
import kotlinx.android.synthetic.main.edit_task_fragment.topic_plainText
import java.text.SimpleDateFormat

class EditTaskFragment: Fragment()
{
    private lateinit var modelTask: ModelTask

    companion object
    {
        fun newInstance() = EditTaskFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_task_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modelTask = ModelTask(DBConnection(context!!))
        val taskId = arguments!!.getInt("task_id")

        val task = modelTask.selectTask(taskId)
        if (task == null)
        {
            Toast.makeText(context!!, "Failed to find the task", Toast.LENGTH_SHORT).show()
            val mainActivity = activity as MainActivity
            mainActivity.showListViewFragment()
            return
        }
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        topic_plainText.setText(task.topic)
        edit_description_plainText.setText(task.description)
        date.text = formatter.format(task.date)

        when(task.priority)
        {
            Priority.HIGH.priority -> radioPriority.check(R.id.status_radioButton)
            Priority.MEDIUM.priority -> radioPriority.check(R.id.status_radioButton2)
            Priority.LOW.priority -> radioPriority.check(R.id.status_radioButton3)
        }

        when(task.status)
        {
            Status.TODO.status -> radioStatus.check(R.id.status_radioButton)
            Status.PENDING.status -> radioStatus.check(R.id.status_radioButton2)
            Status.COMPLETE.status -> radioStatus.check(R.id.status_radioButton3)
        }
        btnEditTask.setOnClickListener {
            editBtnHandler(task)
        }

    }
    fun editBtnHandler(task: Task)
    {
        val name = topic_plainText.text.toString()
        if (name.isEmpty())
        {
            Toast.makeText(context, "Name is required", Toast.LENGTH_SHORT).show()
            return
        }

        val description = edit_description_plainText.text.toString()
        if (description.isEmpty())
        {
            Toast.makeText(context,"Description is required", Toast.LENGTH_SHORT).show()
            return
        }
        val chosenPriorityId = radioPriority.checkedRadioButtonId
        if(chosenPriorityId == -1) {
            Toast.makeText(context,"Choose priority", Toast.LENGTH_SHORT).show()
            return
        }
        val priorityRadio: RadioButton = radioPriority.findViewById(chosenPriorityId)
        val priority: String = priorityRadio.text.toString()

        val chosenStatusId = radioStatus.checkedRadioButtonId
        if (chosenStatusId == -1)
        {
            Toast.makeText(context, "Choose status", Toast.LENGTH_SHORT).show()
            return
        }
        val statusRadio: RadioButton = radioStatus.findViewById(chosenStatusId)
        val status: String = statusRadio.text.toString()

        val updatedTask = Task(name, description, priority, task.date, status, task.id)

        modelTask.editTask(updatedTask)
        Toast.makeText(context, "Task $name has been added", Toast.LENGTH_SHORT).show()
        val mainActivity = activity as MainActivity
        mainActivity.showListViewFragment()

    }
    
}