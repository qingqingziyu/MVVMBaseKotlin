package com.cf.ktx_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 *@作者:陈飞
 *@说明:基类的Fragment
 *@时间:2019/11/27 10:33
 */
abstract class BaseFragment : Fragment(), CoroutineScope by MainScope() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(getLayoutResId(), container, savedInstanceState)
    }

    abstract fun getLayoutResId(): LayoutInflater

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initData()

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}