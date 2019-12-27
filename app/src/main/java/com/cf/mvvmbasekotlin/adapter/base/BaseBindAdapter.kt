package com.cf.mvvmbasekotlin.adapter.base

import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cf.mvvmbasekotlin.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *@作者:陈飞
 *@说明:mvvm的RecyclerView基类适配器，任何适配器将继承此类
 *@时间:2019/12/26 15:18
 */
open class BaseBindAdapter<T>(layoutResId: Int, br: Int) :
    BaseQuickAdapter<T, BaseBindAdapter.BindViewHolder>(layoutResId) {

    private val _br: Int = br


    override fun convert(helper: BindViewHolder?, item: T) {
        helper!!.binding.run {
            setVariable(_br, item)
            executePendingBindings()
        }
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)

        return binding.root.apply {
            //这个Id是用来作标记的
            setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        }
    }


    class BindViewHolder(view: View) : BaseViewHolder(view) {
        val binding: ViewDataBinding
            get() = itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
    }
}