package com.cf.mvvmbasekotlin.core

import com.cf.mvvmbasekotlin.model.ArticleList
import com.cf.mvvmbasekotlin.model.Banner
import com.cf.mvvmbasekotlin.model.api.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    /**
     *@作者:陈飞
     *@说明:获取请求的Banner
     *@时间:2019/12/26 16:49
     */
    @GET("/banner/json")
    suspend fun getBanner(): Response<List<Banner>>


    /**
     *@作者:陈飞
     *@说明:获取文章列表
     *@时间:2019/12/27 9:18
     */
    @GET("/article/list/{page}/json")
    suspend fun getMianArticles(@Path("page") page: Int): Response<ArticleList>
}