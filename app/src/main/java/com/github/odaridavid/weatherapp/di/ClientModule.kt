package com.github.odaridavid.weatherapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.data.weather.OpenWeatherService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientModule {

    @Provides
    @Singleton
    internal fun providesJson(): Json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.OPEN_WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        @ApplicationContext context:Context,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                ChuckerInterceptor.Builder(context = context)
                    .collector(ChuckerCollector(context = context))
                    .maxContentLength(length = 250000L)
                    .redactHeaders(headerNames = emptySet())
                    .alwaysReadResponseBody(enable = false)
                    .build()
            )
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor.Level.NONE
        return HttpLoggingInterceptor().also {
            it.level = level
        }
    }

    @Provides
    @Singleton
    fun provideOpenWeatherService(retrofit: Retrofit): OpenWeatherService =
        retrofit.create(OpenWeatherService::class.java)

}
