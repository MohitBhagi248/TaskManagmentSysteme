package com.example.taskmanagement.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanagement.R
import com.example.taskmanagement.base.BaseActivity
import com.example.taskmanagement.base.BaseViewModel
import com.example.taskmanagement.databinding.ActivityAboutUsBinding
import com.example.taskmanagement.utils.updateStatusBarColor
import com.example.taskmanagement.viewmodel.AddTaskViewModel

class AboutUsActivity : BaseActivity() {


    private var binding: ActivityAboutUsBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)
        updateStatusBarColor(R.color.white, false)

        binding!!.backIV.setOnClickListener{
            finish()
        }


    }

    override fun showTitleBar(): Boolean =false
    override fun createViewModel():AddTaskViewModel {
        return ViewModelProvider.AndroidViewModelFactory(application).create(AddTaskViewModel::class.java)
    }
}


