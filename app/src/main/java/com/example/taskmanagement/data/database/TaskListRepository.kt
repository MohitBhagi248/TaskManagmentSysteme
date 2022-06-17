package com.example.taskmanagement.data.database

import android.app.Application
import com.example.taskmanagement.data.models.Task
import kotlinx.coroutines.*

class TaskListRepository(app: Application) {
    private val mTaskDao = RoomDb.getDatabase(app)!!.taskDao()
    private val mAllTasksLiveData = mTaskDao.getAllTasksLiveData()

    fun getAllTasksLiveData() = mAllTasksLiveData

    fun saveTask(task: Task){
        GlobalScope.launch {
            withContext(Dispatchers.Unconfined){
                mTaskDao.saveTask(task)
            }
        }
    }
     fun deleteTask(task: Task){
        GlobalScope.launch {
                withContext(Dispatchers.Unconfined){
                mTaskDao.deleteTask(task)
            }

        }
    } fun updateTask(task: Task){
        GlobalScope.launch {
            withContext(Dispatchers.Unconfined){
                mTaskDao.updateTask(task)
            }
        }
    }
     fun updateTaskOrder(tasks: List<Task>){
        GlobalScope.launch {
            withContext(Dispatchers.Unconfined){
                mTaskDao.updateTaskOrder(tasks)
            }
        }
    }
    fun getTasksForSearch(searchText: String) = mTaskDao.getTasksForSearch(searchText)

    fun deleteAllTasks(){
        GlobalScope.launch {
            withContext(Dispatchers.Unconfined){
                mTaskDao.deleteAllTasks()
            }
        }
    }

    fun getAllTasks(): ArrayList<Task> {
        val taskList = arrayListOf<Task>()
       GlobalScope.launch {
           val data =launch (Dispatchers.IO){
                taskList.addAll(mTaskDao.getAllTasks())
            }

           data.join()
        }



        return taskList
    }



    /*fun getAllTasksLiveData() = mAllTasksLiveData

    fun deleteAllTasks() = Completable.fromCallable { mTaskDao.deleteAllTasks() }.subscribeOn(Schedulers.io()).subscribe()!!

    fun saveTask(task: Task) = Completable.fromCallable { mTaskDao.saveTask(task) }.subscribeOn(Schedulers.io()).subscribe()!!

    fun deleteTask(task: Task) = Completable.fromCallable { mTaskDao.deleteTask(task) }.subscribeOn(Schedulers.io()).subscribe()!!

    fun updateTask(task: Task) = Completable.fromCallable { mTaskDao.updateTask(task) }.subscribeOn(Schedulers.io()).subscribe()!!

    fun updateTaskOrder(tasks: List<Task>) = Completable.fromCallable { mTaskDao.updateTaskOrder(tasks) }.subscribeOn(Schedulers.io()).subscribe()!!

    fun getTasksForSearch(searchText: String) = mTaskDao.getTasksForSearch(searchText)

    fun getAllTasks(): ArrayList<Task> {
        val taskList = arrayListOf<Task>()
        Observable.fromCallable { mTaskDao.getAllTasks() }.subscribeOn(Schedulers.io())
                .flatMap { tasks -> Observable.fromIterable(tasks) }
                .subscribeBy(onNext = { task -> taskList.add(task) })
        return taskList
    }*/
}