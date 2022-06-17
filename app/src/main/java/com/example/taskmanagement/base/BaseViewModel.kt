package com.example.taskmanagement.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.taskmanagement.data.database.TaskListRepository

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {
    val repository = TaskListRepository(app)
}