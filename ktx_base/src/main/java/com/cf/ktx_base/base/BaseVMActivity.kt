package com.cf.ktx_base.base

import android.app.AppComponentFactory
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

/**
 *@作者:陈飞
 *@说明:带ViewModel的基类Activity
 *@时间:2019/11/27 10:34
 */
abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserve()
        setContentView(getLayoutResId())
        initView()
        initData()
    }


    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}