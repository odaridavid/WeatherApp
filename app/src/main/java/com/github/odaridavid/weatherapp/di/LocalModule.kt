package com.github.odaridavid.weatherapp.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.github.odaridavid.weatherapp.data.weather.local.WeatherDatabase
import com.github.odaridavid.weatherapp.data.weather.local.dao.WeatherDao
import com.github.odaridavid.weatherapp.util.NotificationUtil
import com.github.odaridavid.weatherapp.worker.WeatherUpdateScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext app: Context): WeatherDatabase {
        return Room
            .databaseBuilder(app, WeatherDatabase::class.java, "weather_database")
            .build()
    }

    @Singleton
    @Provides
    fun provideNotificationUtil(@ApplicationContext context: Context): NotificationUtil {
        return NotificationUtil(context)
    }

    @Singleton
    @Provides
    fun provideWeatherUpdateScheduler(workManager: WorkManager): WeatherUpdateScheduler {
        return WeatherUpdateScheduler(workManager)
    }

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun providesWeatherDao(db: WeatherDatabase): WeatherDao {
        return db.weatherDao()
    }
}