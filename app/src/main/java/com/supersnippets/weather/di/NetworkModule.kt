package com.supersnippets.weather.di

import com.supersnippets.weather.BASE_URL
import com.supersnippets.weather.helpers.AccessKeyInterceptor
import com.supersnippets.weather.helpers.NetworkInterceptor
import com.supersnippets.weather.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { NetworkInterceptor(androidContext()) }
    single { AccessKeyInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    println("generated retrofit")
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(
    networkInterceptor: NetworkInterceptor,
    accessKeyInterceptor: AccessKeyInterceptor
): OkHttpClient {
    val logging: HttpLoggingInterceptor? = HttpLoggingInterceptor()
    logging!!.level = HttpLoggingInterceptor.Level.BODY

    println("generated OkHttpClient")
    return OkHttpClient()
        .newBuilder()
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(networkInterceptor)
        .addInterceptor(accessKeyInterceptor)
        .addInterceptor(logging)
        .build()
}

fun provideApi(retrofit: Retrofit): ApiService {
    println("generated ApiService")
    return retrofit.create(ApiService::class.java)
}
