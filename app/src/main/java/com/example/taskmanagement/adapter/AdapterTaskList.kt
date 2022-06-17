package com.example.taskmanagement.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.taskmanagement.R
import com.example.taskmanagement.data.models.Task
import com.example.taskmanagement.databinding.AdapterTaskListBinding
import com.example.taskmanagement.utils.getDate
import com.example.taskmanagement.utils.getTime

class AdapterTaskList(private val list: ArrayList<Task>) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<AdapterTaskListBinding>(LayoutInflater.from(parent.context), R.layout.adapter_task_list, parent, false)
        return BaseViewHolder(binding)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val binding = holder.binding as AdapterTaskListBinding
        val data = list[position]
        binding.titleTV.text=data.title?:""
        binding.timeTV.text="${getDate(data.date)}, ${getTime(data.date)}"
        binding.editIV.setOnClickListener {
            onItemClick(data,1)
        }
        binding.deleteIV.setOnClickListener {
            onItemClick(data,2)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}