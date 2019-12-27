package com.cf.mvvmbasekotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.cf.ktx_base.base.BaseViewModel
import com.cf.mvvmbasekotlin.core.Result
import com.cf.mvvmbasekotlin.model.ArticleList
import com.cf.mvvmbasekotlin.model.Banner
import com.cf.mvvmbasekotlin.model.respository.MainRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *@作者:陈飞
 *@说明: MianViewModel
 *@时间:2019/12/26 16:52
 */
class MainViewModel(private val mainRespository: MainRespository) : BaseViewModel() {


    /**
     *@作者:陈飞
     *@说明:通知更新状态
     *@时间:2019/12/26 17:01
     */
    private val _uiState = MutableLiveData<MainUiModel>()

    val uiState: LiveData<MainUiModel> get() = _uiState


    //当前页数
    private var currentPage = 0


    /**
     *@作者:陈飞
     *@说明:获取banner网络请求
     *@时间:2019/12/27 9:06
     */
    val mBanner: LiveData<List<Banner>> = liveData {
        kotlin.runCatching {
            val data = withContext(Dispatchers.IO) {
                mainRespository.getBanners()
            }
            if (data is Result.Success) emit(data.data)
        }
    }


    fun getActicleList(isRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.Default) {
            //开启加载刷新,加载进度条
            withContext(Dispatchers.Main) {
                emitMainUiState(true)
            }

            //如果刷新，将页数恢复为0
            if (isRefresh) {
                currentPage = 0
            }

            //请求文章列表数据
            val result = mainRespository.getArticleList(currentPage)

            //主线程回传请求数据给Activity
            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    val articleList = result.data

                    if (articleList.offset >= articleList.total) {
                        emitMainUiState(showLoading = false, showEnd = true)
                        return@withContext
                    }
                    currentPage++

                    //更新UI
                    emitMainUiState(
                        showLoading = false,
                        showSuccess = articleList,
                        isRefresh = isRefresh
                    )
                } else if (result is Result.Error) {
                    //更新UI
                    emitMainUiState(showLoading = false, showError = result.exception.message)
                }
            }
        }
    }


    /**
     *@作者:陈飞
     *@说明:状态方法，用来更新UI
     *@时间:2019/12/26 16:58
     */
    private fun emitMainUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: ArticleList? = null,
        showEnd: Boolean = false,
        isRefresh: Boolean = false
    ) {
        val uiModel = MainUiModel(showLoading, showError, showSuccess, showEnd, isRefresh)
        //将状态加入到LiveDate
        _uiState.value = uiModel
    }

    /**
     *@作者:陈飞
     *@说明:定义一个显示状态Bean
     *@时间:2019/12/26 16:56
     */
    data class MainUiModel(
        val showLoading: Boolean,//是否加载完成
        val showError: String?,//显示错误提示
        val showSuccess: ArticleList?,//请求数据
        val showEnd: Boolean,//加载更多
        val isRefresh: Boolean//是否刷新
    )
}