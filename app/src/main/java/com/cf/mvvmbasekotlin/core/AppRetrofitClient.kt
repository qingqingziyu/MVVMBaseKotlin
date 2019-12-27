package com.cf.mvvmbasekotlin.core

import android.content.SharedPreferences
import com.cf.mvvmbasekotlin.App
import com.cf.mvvmbasekotlin.util.NetWorkUtils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File

/**
 *@作者:陈飞
 *@说明:请求类
 *@时间:2019/12/26 16:09
 */
object AppRetrofitClient : BaseRetrofitClient() {

    /**
     *@作者:陈飞
     *@说明:retrofit  请求
     *@时间:2019/12/26 16:10
     */
    val services by lazy { getService(ApiService::class.java, ApiService.BASE_URL) }


    private val cookieJar by lazy {
        PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(App.CONTEXT)
        )
    }

    override fun handlerBuidler(build: OkHttpClient.Builder) {
        //设置缓存
        val httpCacheDirectory = File(App.CONTEXT.cacheDir, "response")
        val cacheSize = 10 * 1024 * 1024L
        val cache = Cache(httpCacheDirectory, cacheSize)
        build.cache(cache)
            .cookieJar(cookieJar)
            .addInterceptor { chain ->
                var request = chain.request()
                if (!NetWorkUtils.isNetworkAvailable(App.CONTEXT)) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
                var response = chain.proceed(request)
                if (!NetWorkUtils.isNetworkAvailable(App.CONTEXT)) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
                response
            }
    }
}