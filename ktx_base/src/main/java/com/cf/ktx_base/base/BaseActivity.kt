package com.cf.ktx_base.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cf.ktx_base.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 *@作者:陈飞
 *@说明:基类的Activity
 *@时间:2019/11/25 16:17
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() { //MainScope:作用域

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun getLayoutResId(): Int

    protected fun startActivity(z: Class<*>) {
        startActivity(Intent(this, z))
    }

    protected fun startActivity(z: Class<*>, name: String, value: Boolean) {
        val intent = Intent(this, z).putExtra(name, value)
        startActivity(Intent(this, z))
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }



}
