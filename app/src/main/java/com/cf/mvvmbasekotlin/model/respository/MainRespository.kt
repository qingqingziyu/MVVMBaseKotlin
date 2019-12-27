package com.cf.mvvmbasekotlin.model.respository

import com.cf.mvvmbasekotlin.core.AppRetrofitClient
import com.cf.mvvmbasekotlin.core.Result
import com.cf.mvvmbasekotlin.model.ArticleList
import com.cf.mvvmbasekotlin.model.Banner
import com.cf.mvvmbasekotlin.model.api.BaseRepository

/**
 *@作者:陈飞
 *@说明:首页请求类，在这里作为Model架构
 *@时间:2019/12/26 16:44
 */
class MainRespository : BaseRepository() {

    /**
     *@作者:陈飞
     *@说明:请求Banner
     *@时间:2019/12/26 16:50
     */
    suspend fun getBanners(): Result<List<Banner>> {
        return safeApiCall(call = { requestBanners() }, errorMessage = "未知错误")
    }

    /**
     *@作者:陈飞
     *@说明:请求Banner
     *@时间:2019/12/26 16:50
     */
    private suspend fun requestBanners(): Result<List<Banner>> =
        executeResponse(AppRetrofitClient.services.getBanner())


    /**
     *@作者:陈飞
     *@说明:获取文章列表
     *@时间:2019/12/27 9:14
     */
    suspend fun getArticleList(page: Int): Result<ArticleList> {
        return safeApiCall(call = { requestArticleList(page) }, errorMessage = "未知错误")
    }

    /**
     *@作者:陈飞
     *@说明:获取文章列表
     *@时间:2019/12/27 9:18
     */
    private suspend fun requestArticleList(page: Int): Result<ArticleList> =
        executeResponse(AppRetrofitClient.services.getMianArticles(page))
}