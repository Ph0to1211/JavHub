package com.jadesoft.javhub.data.di

import android.content.Context
import android.net.ConnectivityManager
import com.jadesoft.javhub.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://www.javbus.com/"
    private const val CACHE_SIZE = 15 * 1024 * 1024L // 15MB

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    fun provideHeaders(): Map<String, String> {
        return mapOf(
            "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
            "Accept-Language" to "zh-CN,zh;q=0.9",
            "Referer" to "https://www.javbus.com/"
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        headers: Map<String, String>
    ): OkHttpClient {
        // 创建缓存目录
        val cacheDir = File(context.cacheDir, "http_cache")
        val cache = Cache(cacheDir, CACHE_SIZE)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val headerInterceptor = Interceptor {chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
            headers.forEach { (key, value) ->
                requestBuilder.addHeader(key, value)
            }
            val newRequest = requestBuilder.build()
            chain.proceed(newRequest)
        }

        // 缓存策略拦截器
        val cacheInterceptor = Interceptor { chain ->
            var request = chain.request()

            // 如果请求头中没有自定义 Cache-Control，才应用默认逻辑
            if (request.header("Cache-Control") == null) {
                if (!isNetworkAvailable(context)) {
                    // 无网络时强制使用缓存
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
            }

            val response = chain.proceed(request)

            // 仅在未自定义 Cache-Control 时设置响应缓存
            if (response.header("Cache-Control") == null) {
                if (isNetworkAvailable(context)) {
                    val maxAge = 60 // 默认缓存1分钟
                    response.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .removeHeader("Pragma")
                        .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 7 // 无网络时缓存一周
                    response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .removeHeader("Pragma")
                        .build()
                }
            } else {
                response
            }
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor) // 添加请求头拦截器
            .addInterceptor(cacheInterceptor)
            .addNetworkInterceptor(cacheInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

}