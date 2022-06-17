package com.example.taskmanagement.activity

import android.os.Bundle
import android.os.Handler
import com.example.taskmanagement.base.BaseActivity
import com.example.taskmanagement.R
import com.example.taskmanagement.base.BaseViewModel
import com.example.taskmanagement.utils.updateStatusBarColor

class SplashActivity : BaseActivity() {

    private val handler = Handler()
    override fun showTitleBar(): Boolean = false
    override fun createViewModel(): BaseViewModel {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        updateStatusBarColor(R.color.white, false)

    }


    override fun onResume() {
        super.onResume()
        handler.postDelayed({
            runOnUiThread {
                initFCM()
            }
        }, 2000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

}