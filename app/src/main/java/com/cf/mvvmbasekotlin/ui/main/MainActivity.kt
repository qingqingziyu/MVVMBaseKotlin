package com.cf.mvvmbasekotlin.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cf.ktx_base.base.BaseVMActivity
import com.cf.ktx_base.ext.dp2px
import com.cf.mvvmbasekotlin.R
import com.cf.mvvmbasekotlin.adapter.MainArticleAdapter
import com.cf.mvvmbasekotlin.util.GlideImageLoader
import com.cf.wanzhuanandroidapp.view.CustomLoadMoreView
import com.cf.wanzhuanandroidapp.view.SpaceItemDecoration
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_main.*
import luyao.util.ktx.ext.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseVMActivity<MainViewModel>() {


    //获取当前的传入的 MainViewModel
    private val mViewMode: MainViewModel by viewModel()

    //创建banner集合
    private val bannerImages = mutableListOf<String>()

    private val bannerTitles = mutableListOf<String>()

    private val bannerUrls = mutableListOf<String>()

    //适配器
    private val mainArticleAdapter by lazy { MainArticleAdapter() }

    //创建首页轮播栏
    private val mBannerView by lazy { Banner(this) }


    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initView() {

        //设置适配器
        mainArticleAdapter.run {
            //添加头部
            addHeaderView(mBannerView)
            //刷新view
            setLoadMoreView(CustomLoadMoreView())
            //加载更多事件
            setOnLoadMoreListener({ more() }, mRecyclerView)
        }

        //设置RecyclerView
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            //设置分割线
            addItemDecoration(SpaceItemDecoration(mRecyclerView.dp2px(10)))
            //设置适配器
            adapter = mainArticleAdapter
        }

        //设置轮播栏
        mBannerView.run {
            //设置轮播栏的宽高
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                mBannerView.dp2px(200)
            )
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setImageLoader(GlideImageLoader())
            setOnBannerListener { position ->
                kotlin.run {
                    //"..轮播栏点击事件.."
                }
            }
        }

        //刷新
        mainRefreshLayout.run {
            setOnRefreshListener {
                refresh()
            }
        }
    }

    override fun initData() {
        //开始刷新
        refresh()
    }

    //刷新
    fun refresh() {
        mainArticleAdapter.setEnableLoadMore(false)
        mViewMode.getActicleList(true)
    }

    //加载
    fun more() {
        mViewMode.getActicleList(false)
    }

    override fun onStart() {
        super.onStart()
        mBannerView.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        mBannerView.stopAutoPlay()
    }


    /**
     *@作者:陈飞
     *@说明:请求回调
     *@时间:2019/12/27 9:34
     */
    override fun startObserve() {
        mViewMode.apply {
            //Banner请求回调
            mBanner.observe(this@MainActivity, Observer { t ->
                t?.let {
                    setBanner(it)
                }
            })
            //文章请求回调
            uiState.observe(this@MainActivity, Observer {
                //是否开启刷新
                mainRefreshLayout.isRefreshing = it.showLoading
                //数据请求成功
                it.showSuccess?.let { article ->
                    mainArticleAdapter.run {
                        //如果是刷新，那么覆盖原来的
                        if (it.isRefresh) {
                            replaceData(article.datas)
                        } else { //如果是加载，从尾部添加新数据
                            addData(article.datas)
                        }
                        //开启加载
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }

                    //如果请求结束，关闭加载更多
                    if (it.showEnd) mainArticleAdapter.loadMoreEnd()

                    //如果请求报错，那么提示
                    it.showError?.let { message ->
                        this@MainActivity?.toast(if (message.isBlank()) "网络异常" else message)
                    }
                }
            })
        }
    }

    /**
     *@作者:陈飞
     *@说明:设置Banner数据
     *@时间:2019/12/27 9:39
     */
    private fun setBanner(bannerList: List<com.cf.mvvmbasekotlin.model.Banner>) {
        for (banner in bannerList) {
            bannerImages.add(banner.imagePath)
            bannerTitles.add(banner.title)
            bannerUrls.add(banner.url)
        }

        //装填banner数据
        mBannerView.setImages(bannerImages)
            .setBannerTitles(bannerTitles)
            .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            .setDelayTime(3000)
        mBannerView.start()
    }

}
