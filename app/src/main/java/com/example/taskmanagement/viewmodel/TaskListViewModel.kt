package com.example.taskmanagement.viewmodel

import android.app.Application
import com.example.taskmanagement.base.BaseViewModel
import com.example.taskmanagement.data.models.Task

class TaskListViewModel(app: Application) : BaseViewModel(app) {
    val liveData = repository.getAllTasksLiveData()

    fun updateTaskOrder(tasks: List<Task>) = repository.updateTaskOrder(tasks)

    fun deleteTask(task: Task) = repository.deleteTask(task)

    fun saveTask(task: Task) = repository.saveTask(task)
}