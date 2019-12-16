package com.example.taskmenager.Model

class PrioritySort {
    companion object : Comparator<Task>
    {
        override fun compare(a: Task, b: Task): Int {
            if(a.priority == Priority.HIGH.priority && (b.priority == Priority.MEDIUM.priority || b.priority == Priority.LOW.priority)) {
                return -1
            }

            if(a.priority == Priority.MEDIUM.priority && b.priority == Priority.LOW.priority) {
                return -1
            }

            if(a.priority == Priority.MEDIUM.priority && b.priority == Priority.HIGH.priority) {
                return 1
            }

            if(a.priority == Priority.LOW.priority && (b.priority == Priority.MEDIUM.priority || b.priority == Priority.HIGH.priority)) {
                return 1
            }

            return 0
        }
    }
}