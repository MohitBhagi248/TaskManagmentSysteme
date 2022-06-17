package com.example.taskmanagement.adapter

import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


open class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>(), View.OnClickListener {
    private var onItemClickListener: OnItemClickListener? = null
    private var onPageEndListener: OnPageEndListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(null)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnPageEndListener(onPageEndListener: OnPageEndListener) {
        this.onPageEndListener = onPageEndListener
    }

    fun onItemClick(vararg itemData: Any) {
        if (onItemClickListener != null) {
            onItemClickListener!!.onItemClick(*itemData)
        }
    }

    fun onPageEnd(vararg itemData: Any) {
        if (onPageEndListener != null) {
            onPageEndListener!!.onPageEnd(*itemData)
        }
    }

    override fun onClick(v: View) {

    }

    interface OnItemClickListener {
        fun onItemClick(vararg itemData: Any)
    }

    interface OnPageEndListener {
        fun onPageEnd(vararg itemData: Any)
    }

    abstract inner class clickListener(var dataBinding: ViewDataBinding, protected var adapterPosition: Int) : View.OnClickListener {
        abstract fun onClickItem(dataBinding: ViewDataBinding, adapterPosition: Int)
    }

    abstract inner class itemSelected protected constructor(var dataBinding: ViewDataBinding, protected var adapterPosition: Int) : AdapterView.OnItemSelectedListener

    abstract inner class itemClick(var dataBinding: ViewDataBinding, internal var adapterPosition: Int) : AdapterView.OnItemClickListener

    abstract inner class textChange protected constructor(var dataBinding: ViewDataBinding, var position: Int) : TextWatcher

    abstract inner class checkChange protected constructor(var dataBinding: ViewDataBinding, position: Int) : CompoundButton.OnCheckedChangeListener {
        var position = 0

        init {
            this.position = position

        }
    }
}
