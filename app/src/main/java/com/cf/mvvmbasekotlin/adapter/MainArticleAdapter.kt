package com.cf.mvvmbasekotlin.adapter

import androidx.annotation.LayoutRes
import com.cf.mvvmbasekotlin.BR
import com.cf.mvvmbasekotlin.R
import com.cf.mvvmbasekotlin.adapter.base.BaseBindAdapter
import com.cf.mvvmbasekotlin.model.Article

/**
 *@作者:陈飞
 *@说明:首页适配器
 *@时间:2019/12/27 9:55
 * @param BR指向的是dataBinding中data里面variable的name
 */
class MainArticleAdapter(layoutResId: Int = R.layout.item_article) :
    BaseBindAdapter<Article>(layoutResId, BR.article) {

    //是否显示
    private var showStar = true

    fun showStar(showStar: Boolean) {
        this.showStar = showStar
    }


    override fun convert(helper: BindViewHolder?, item: Article) {
        super.convert(helper, item)
        //点击关注加入事件
        helper!!.addOnClickListener(R.id.articleStar)
        //点击条目事件
        helper!!.addOnClickListener(R.id.main_item_controll)

        if (showStar) {
            helper.setImageResource(
                R.id.articleStar,
                if (item.collect) R.mipmap.timeline_like_pressed else R.mipmap.timeline_like_normal
            )
        } else {
            helper.setVisible(R.id.articleStar, false)
        }

        helper.setText(
            R.id.articleAuthor,
            if (item.author.isBlank()) "分享者：${item.shareUser}" else item.author
        )
    }
}
