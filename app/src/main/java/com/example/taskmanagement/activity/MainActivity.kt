package com.example.taskmanagement.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.taskmanagement.R
import com.example.taskmanagement.adapter.AdapterTaskList
import com.example.taskmanagement.adapter.BaseAdapter
import com.example.taskmanagement.base.BaseActivity
import com.example.taskmanagement.data.models.Task
import com.example.taskmanagement.databinding.ActivityMainBinding
import com.example.taskmanagement.utils.onTextChanged
import com.example.taskmanagement.utils.setBounceAnim
import com.example.taskmanagement.utils.showCommonDialog
import com.example.taskmanagement.utils.updateStatusBarColor
import com.example.taskmanagement.viewmodel.TaskListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : BaseActivity(), BaseAdapter.OnItemClickListener {
    var mTaskList = arrayListOf<Task>()

    private var binding: ActivityMainBinding? = null
    override fun showTitleBar(): Boolean = false
    private lateinit var mViewModel: TaskListViewModel
    override fun createViewModel() =
        ViewModelProvider.AndroidViewModelFactory(application).create(TaskListViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        updateStatusBarColor(R.color.white, false)

        mViewModel = createViewModel()

        getList()
        handleClickListener()
        binding!!.searchET.onTextChanged{ string ->
            if (string.isNotEmpty()) {

                val filteredList = mTaskList.filter {
                    it.title.lowercase()
                        .contains(string.lowercase(Locale.getDefault())) }.toMutableList() as ArrayList<Task>
                setAdapter(filteredList)
            } else {
                setAdapter(mTaskList)
            }
        }

    }

    private fun getList() {

        mViewModel.liveData.observe(this, Observer {list->
            mTaskList.clear()
            mTaskList.addAll(list)
            setAdapter(mTaskList)

        })
    }

    private fun setAdapter(mTaskList: ArrayList<Task>) {

        if (mTaskList.isNotEmpty()) {
            binding!!.emptyG.visibility = View.GONE
        } else {
            binding!!.emptyG.visibility = View.VISIBLE

        }

        val adapter =AdapterTaskList(mTaskList)
        binding!!.taskRV.adapter=adapter
        adapter.setOnItemClickListener(this)
    }

    private fun handleClickListener() {
        binding!!.addTaskTV.setOnClickListener {
            binding!!.addTaskTV.setBounceAnim()
            lifecycleScope.launch(Dispatchers.Main) {
                delay(400)
                runOnUiThread {
                    startActivity(Intent(this@MainActivity, AddTaskActivity::class.java))
                }
            }
        }
        binding!!.aboutUsTV.setOnClickListener {
            binding!!.aboutUsTV.setBounceAnim()
            lifecycleScope.launch(Dispatchers.Main) {
                delay(400)
                runOnUiThread {
                    startActivity(Intent(this@MainActivity, AboutUsActivity::class.java))
                }
            }
        }
    }

    override fun onItemClick(vararg itemData: Any) {
        if(itemData.isNotEmpty()){
            val data = itemData[0] as Task
            val type=itemData[1] as Int
            when(type){
                1->{
                    updateTask(data)
                }
                2->{
                    this.showCommonDialog(
                        "Are you sure ?\nYou want to delete task!",
                        title = "Delete Task",
                        negativeBtnText = "",
                        handleClick = {
                            if(it){
                                deleteTask(data)
                            }
                        })

                }
            }
        }
    }

    private fun updateTask(data: Task) {
        val intent = Intent(this, AddTaskActivity::class.java).apply {
            putExtra("id", data.id)
            putExtra("title", data.title)
            putExtra("note", data.note)
            putExtra("position", data.position)
            putExtra("time_stamp", data.timeStamp)
            if (data.date != 0L) {
                putExtra("date", data.date)
            }
            putExtra("isUpdate", true)
        }
        startActivity(intent)
    }

    private fun deleteTask(data: Task) {
        startProgressDialog()
        lifecycleScope.launch {
            val job = launch(Dispatchers.Unconfined) {
                mViewModel.deleteTask(data)
            }
            job.join()

            runOnUiThread {
                stopProgressDialog()
            }
        }

        getList()

    }


}