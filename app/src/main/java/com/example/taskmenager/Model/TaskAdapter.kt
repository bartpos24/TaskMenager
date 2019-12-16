package com.example.taskmenager.Model

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taskmenager.DB.DBConnection
import com.example.taskmenager.EditTaskFragment
import com.example.taskmenager.MainActivity
import com.example.taskmenager.R
import java.text.SimpleDateFormat

class TaskAdapter(private val context: Context, private var dataSource: List<Task>): BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mainActivity: MainActivity = context as MainActivity
    private val modelTask =  ModelTask(DBConnection(context))


    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.task_item, parent, false)
        val nameTextView = rowView.findViewById(R.id.topic_content) as TextView
        val priorityTextView = rowView.findViewById(R.id.priority_content) as TextView
        val statusTextView = rowView.findViewById(R.id.status_content) as TextView
        val dateTextView = rowView.findViewById(R.id.date_content) as TextView
        val task = getItem(position) as Task
        rowView.setOnClickListener {
            val fragment = EditTaskFragment.newInstance()
            val bundle = Bundle()
            bundle.putInt("task_id", task.id)
            fragment.arguments = bundle
            showFragment(fragment)
        }
        val delButton = rowView.findViewById<Button>(R.id.deleteBtn)
        delButton.setOnClickListener {
            modelTask.deleteTask(task)
            dataSource = modelTask.getAllTasks()
            notifyDataSetChanged()
            Toast.makeText(context, "Task ${task.topic} has been deleted", Toast.LENGTH_SHORT).show()
        }

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        nameTextView.text = task.topic
        priorityTextView.text = task.priority
        statusTextView.text = task.status
        dateTextView.text = formatter.format(task.date)
        return rowView
    }

    fun showFragment(fragment: Fragment)
    {
        val manager = mainActivity.supportFragmentManager
        val fragmentTransaction = manager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment,fragment)
        fragmentTransaction.commit()
    }
}