package com.example.taskmenager.Model

import java.util.*

class Task(topic:String, description:String, priority:String, date:Date = Date(), status: String = Status.TODO.status, id: Int = 0
)
{

    var topic: String = topic
    var description: String = description
    var priority: String = priority
    val date: Date = date
    var status: String = status
    val id = id


}