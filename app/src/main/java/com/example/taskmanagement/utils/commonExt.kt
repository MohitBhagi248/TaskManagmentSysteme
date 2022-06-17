package com.example.taskmanagement.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.DialogCommonMsgBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("InlinedApi")
fun AppCompatActivity.setSystemBarTheme(isStatusBarFontDark: Boolean) {

    window.decorView.systemUiVisibility =
        if (isStatusBarFontDark) {
            0
        } else {
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        }
}


fun AppCompatActivity.updateStatusBarColor(
    @ColorRes colorId: Int,
    isStatusBarFontDark: Boolean = true,
    statusBarColor: Int = R.color.transparent
) {
    val window = window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.statusBarColor = ContextCompat.getColor(this, statusBarColor)
    window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)
    window.setBackgroundDrawable(ContextCompat.getDrawable(this, colorId))
    setSystemBarTheme(isStatusBarFontDark)
}


fun View.setBounceAnim() {
    this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.bounce_anim))
}


@SuppressLint("SimpleDateFormat")
fun getTime(time: Long): String {

    val timeFormat = SimpleDateFormat("hh:mm aa")

    return timeFormat.format(time)
}

@SuppressLint("SimpleDateFormat")
fun getDate(date: Long): String {

    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(date)
}


fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(s.toString())
        }
    })
}

fun View.visibilityView(isShow: Boolean) {
    if (isShow) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}


fun Context.showCommonDialog(
    message: String = "",
    title: String = this.getString(R.string.app_name),
    positiveBtnText: String = this.getString(R.string.ok),
    negativeBtnText: String = this.getString(R.string.cancel),
    handleClick: (positiveClick: Boolean) -> Unit = {},
    isCancelable: Boolean = true
) {

    val customDialog = Dialog(this)
    val dialogBinding = DataBindingUtil.inflate<DialogCommonMsgBinding>(
        LayoutInflater.from(this), R.layout.dialog_common_msg, null, false
    )
    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    customDialog.setContentView(dialogBinding.root)
    Objects.requireNonNull<Window>(customDialog.window).setBackgroundDrawable(
        ColorDrawable(
            Color.TRANSPARENT
        )
    )
    customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    customDialog.setCancelable(isCancelable)

    customDialog.dismiss()

    val lpr = WindowManager.LayoutParams()
    lpr.copyFrom(customDialog.window!!.attributes)
    lpr.width = WindowManager.LayoutParams.MATCH_PARENT
    lpr.height = WindowManager.LayoutParams.WRAP_CONTENT
    lpr.windowAnimations = R.style.bottomStyle
    lpr.gravity = Gravity.CENTER
    customDialog.window!!.attributes = lpr

    dialogBinding.msgTV.text = message
    dialogBinding.titleTV.text = title
    dialogBinding.okTV.text = positiveBtnText
    dialogBinding.cancelTV.text = negativeBtnText

    dialogBinding.okTV.visibilityView(positiveBtnText.isNotEmpty())
    dialogBinding.cancelTV.visibilityView(negativeBtnText.isNotEmpty())

    dialogBinding.cancelTV.setOnClickListener {
        handleClick(false)
        customDialog.dismiss()
    }
    dialogBinding.okTV.setOnClickListener {
        handleClick(true)
        customDialog.dismiss()
    }


    customDialog.show()
}
