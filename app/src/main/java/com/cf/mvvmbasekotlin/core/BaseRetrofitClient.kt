package com.cf.mvvmbasekotlin.core

import android.os.Build
import com.cf.mvvmbasekotlin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *@作者:陈飞
 *@说明:retrofit请求基类
 *@时间:2019/12/26 15:59
 */
abstract class BaseRetrofitClient {

    companion object {
        //超时时间
        private const val TIME_OUT = 5
    }

    /**
     *@作者:陈飞
     *@说明:OkHttp设置
     *@时间:2019/12/26 16:01
     */
    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            //判断是否是测试模式
            if (BuildConfig.DEBUG) { //是测试模式显示详细打印
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            }

            //日志拦截器
            builder.addInterceptor(httpLoggingInterceptor)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)


            //暴露OkHttp的实例，可供其他的设置
            handlerBuidler(builder)

            return builder.build()
        }


    /**
     *@作者:陈飞
     *@说明:暴露OkHttp的实例，可供其他的设置
     *@时间:2019/12/26 16:03
     */
    protected abstract fun handlerBuidler(build: OkHttpClient.Builder)


    /**
     *@作者:陈飞
     *@说明:设置Retrofit
     *@时间:2019/12/26 16:07
     */
    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }

}