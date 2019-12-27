package com.cf.mvvmbasekotlin.model.api

/**
 *@作者:陈飞
 *@说明:通用请求bean
 *@时间:2019/12/26 15:31
 */
data class Response<out T>(val errorCode: Int, val errorMsg: String, val data: T)