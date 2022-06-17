package com.example.taskmanagement.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanagement.R
import com.example.taskmanagement.base.BaseActivity
import com.example.taskmanagement.base.BaseViewModel
import com.example.taskmanagement.data.models.Task
import com.example.taskmanagement.databinding.ActivityAddTaskBinding
import com.example.taskmanagement.utils.getDate
import com.example.taskmanagement.utils.getTime
import com.example.taskmanagement.utils.updateStatusBarColor
import com.example.taskmanagement.utils.visibilityView
import com.example.taskmanagement.viewmodel.AddTaskViewModel
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

class AddTaskActivity : BaseActivity(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    private var mId: Long = 0
    private var mDate: Long = 0
    private var mPosition: Int = 0
    private var mTimeStamp: Long = 0
    private var mTitle=""
    private var mNote=""

var isUpdate:Boolean = false

    override fun showTitleBar(): Boolean =false
    override fun createViewModel():AddTaskViewModel {
        return ViewModelProvider.AndroidViewModelFactory(application).create(AddTaskViewModel::class.java)
    }

    private val isValid: Boolean get() {

        when {
            binding!!.taskTitleET.text.toString().isEmpty() -> {
                Toast.makeText(this,"Please enter title",Toast.LENGTH_SHORT).show()
            }
            /*binding!!.addNoteET.text.toString().isEmpty() -> {
                Toast.makeText(this,"Please add notes",Toast.LENGTH_SHORT).show()
            }
            binding!!.reminderTV.text.toString().isEmpty() -> {
                Toast.makeText(this,"Please enter title",Toast.LENGTH_SHORT).show()
            }*/
            else->{
                return true
            }

        }

        return false
    }
    private var binding: ActivityAddTaskBinding? = null
    lateinit var mCalendar: Calendar

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
//        setContentView(R.layout.activity_add_task)
        updateStatusBarColor(R.color.white, false)

        mCalendar = Calendar.getInstance()
        initUI()

        intent?.extras?.let {
            mId = intent.getLongExtra("id", 0)
            mTitle = intent.getStringExtra("title").toString()
            mNote = intent.getStringExtra("note").toString()
            mDate = intent.getLongExtra("date", 0)
            mPosition = intent.getIntExtra("position", 0)
            mTimeStamp = intent.getLongExtra("time_stamp", 0)
            isUpdate=intent.getBooleanExtra("isUpdate",false)
        }
        if(isUpdate){
            binding!!.titleTV.text="Update Task"
            binding!!.addTaskTV.text="Update Task"
            binding!!.taskTitleET.setText(mTitle)
            binding!!.addNoteET.setText(mNote)

            if(mDate!=0L){
                binding!!.reminderTV.text = "${getDate(mDate)}, ${getTime(mDate)}"
            }
            binding!!.removeTimeIV.visibilityView(reminderTV.text.toString().trim().isNotEmpty())

        }else{
            binding!!.titleTV.text="New Task"
            binding!!.addTaskTV.text="Add Task"

        }
    }



    private fun initUI() {

        binding!!.reminderTV.setOnClickListener {
            showDatePickerDialog()
        }

        binding!!.backIV.setOnClickListener {
           finish()
        }

        binding!!.removeTimeIV.setOnClickListener {
            binding!!.reminderTV.text = ""
            binding!!.removeTimeIV.visibility = View.GONE
        }

        binding!!.addTaskTV.setOnClickListener {
            /*binding!!.addNoteET.text.toString().trim()
            binding!!.reminderTV.text.toString().trim()*/

           if(isValid){
               val data =Task()

               data.title=binding!!.taskTitleET.text.toString().trim()
               data.note=binding!!.addNoteET.text.toString().trim()

               if(isUpdate){
                   data.id=mId
                   data.date=mDate

               }else{
                   if (binding!!.reminderTV.text.toString().trim().isNotEmpty()) {
                       data.date = mCalendar.timeInMillis
                   }
                   if (data.date != 0L && data.date <= Calendar.getInstance().timeInMillis) {
                       data.date = 0
                       Toast.makeText(this,"Error! You have selected an incorrect time!",Toast.LENGTH_SHORT).show()
                   }
               }


               val viewModel = createViewModel()
               if(isUpdate){
                   viewModel.updateTask(data)
               }else{
                   viewModel.saveTask(data)
               }
               finish()
           }
        }
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        DatePickerDialog(this, R.style.DatePicker_Light, this, year, month, day).apply {
            window?.attributes?.windowAnimations = R.style.DialogAnimation
            show()
            window?.setLayout(
                resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._200sdp),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(
                    this@AddTaskActivity,
                    R.color.purple
                )
            )
            getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(
                    this@AddTaskActivity,
                    R.color.purple
                )
            )
            getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.TRANSPARENT)
            getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun showTimePickerDialog() {
//        val timeFormatKey = PreferenceHelper.getInstance().getInt(PreferenceHelper.TIME_FORMAT_KEY)
        lateinit var timePickerDialog: TimePickerDialog

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE) + 1


        timePickerDialog= TimePickerDialog(this, this, hour, minute, true)
        timePickerDialog.apply {
            window?.attributes?.windowAnimations = R.style.DialogAnimation
            show()
            window?.setLayout(
                resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._200sdp),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(
                    this@AddTaskActivity,
                    R.color.purple
                )
            )
            getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(
                    this@AddTaskActivity,
                    R.color.purple
                )
            )
            getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.TRANSPARENT)
            getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.TRANSPARENT)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(timePicker: TimePicker, hourOfDay: Int, minute: Int) {
        mCalendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }


        if(isUpdate){
            mDate=mCalendar.timeInMillis
        }

        binding!!.reminderTV.text = "${getDate(mCalendar.timeInMillis)}, ${getTime(mCalendar.timeInMillis)}"
        binding!!.removeTimeIV.visibility = View.VISIBLE

    }

    override fun onDateSet(datePicker: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        mCalendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        showTimePickerDialog()
    }


}