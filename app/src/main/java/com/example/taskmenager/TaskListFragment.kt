package com.example.taskmenager.DB

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.taskmenager.MainActivity
import com.example.taskmenager.Model.ModelTask
import com.example.taskmenager.Model.Task
import com.example.taskmenager.Model.TaskAdapter
import com.example.taskmenager.R

class TaskListFragment : Fragment()
{
    private lateinit var modelTask: ModelTask
    private lateinit var listView: ListView
    private val listOfTask = mutableListOf<Task>()
    private lateinit var adapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = activity?.findViewById(R.id.tasksListView)!!
        modelTask = ModelTask(DBConnection(context!!))
        AddToListView(modelTask.getAllTasks())
    }

    fun AddToListView(listView_tasks: List<Task>)
    {
        adapter = TaskAdapter(context as MainActivity, listView_tasks)
        listView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        ClearListView()
    }

    fun ClearListView()
    {
        listOfTask.clear()
        if (modelTask == null)
        {
            modelTask = ModelTask(DBConnection(context!!))
        }
        listOfTask.addAll(modelTask.getAllTasks())
        adapter.notifyDataSetChanged()
    }

}