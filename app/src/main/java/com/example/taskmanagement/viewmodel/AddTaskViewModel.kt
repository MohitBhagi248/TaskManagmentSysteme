package com.example.taskmanagement.viewmodel

import android.app.Application
import com.example.taskmanagement.base.BaseViewModel
import com.example.taskmanagement.data.models.Task

class AddTaskViewModel(app: Application) : BaseViewModel(app) {
    fun saveTask(task: Task) = repository.saveTask(task)
    fun updateTask(task: Task) = repository.updateTask(task)
}