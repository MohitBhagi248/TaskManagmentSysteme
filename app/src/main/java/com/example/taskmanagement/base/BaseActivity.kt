package com.example.taskmanagement.base

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanagement.BuildConfig
import com.example.taskmanagement.activity.MainActivity
import com.example.taskmanagement.activity.MainIntroActivity
import com.example.taskmanagement.R
import com.example.taskmanagement.data.database.RoomDb
import com.example.taskmanagement.data.models.Task
import com.example.taskmanagement.utils.Prefs
import com.example.taskmanagement.utils.updateStatusBarColor


abstract class BaseActivity : AppCompatActivity() {
    var store: Prefs? = null
    var dataBase: RoomDb? = null
    private var progressDialog: Dialog? = null
    private var txtMsgTV: TextView? = null
//    val viewModel by viewModel<BaseViewModel>()
    private var exit: Boolean = false

    private var inputMethodManager: InputMethodManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateStatusBarColor(R.color.white, true)

        strictModeThread()
        transitionSlideInHorizontal()

        if (showTitleBar()) supportActionBar?.show()
        else supportActionBar?.hide() //AndroidInjection.inject(this)
        store = Prefs(this)
        dataBase = RoomDb.getDatabase(this)
        progressDialog()

    }
    abstract fun showTitleBar(): Boolean

    private fun strictModeThread() {
        val policy = StrictMode.ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    private fun transitionSlideInHorizontal() {
        this.overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }



    fun initFCM() {
        if(!store!!.getBoolean("isFirstTime",false)){
            gotoNext()
        }else{
            gotoMainActivity()
        }
    }

    private fun gotoNext() {

        startActivityForResult(Intent(this, MainIntroActivity::class.java),12054)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            when (requestCode) {
                12054 -> {
                    store!!.save("isFirstTime",true)
                    gotoMainActivity()
                }
            }
        }
    }
    abstract fun createViewModel(): BaseViewModel

    private fun gotoMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun progressDialog() {
        progressDialog = Dialog(this)
        val view = View.inflate(this, R.layout.progress_dialog, null)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(view)
        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        txtMsgTV = view.findViewById<View>(R.id.txtMsgTV) as TextView?
        progressDialog!!.setCancelable(false)
    }

    fun startProgressDialog() {
        if (progressDialog != null && !progressDialog!!.isShowing) {
            try {
                progressDialog!!.show()
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
            }

        }
    }

    fun stopProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            try {
                progressDialog!!.dismiss()
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
            }

        }
    }



}