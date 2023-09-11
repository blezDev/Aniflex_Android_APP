package com.blez.aniplex_clone.di

import android.content.Context
import androidx.room.Room
import com.blez.aniplex_clone.Network.AnimeInterface
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.db.AppDB
import com.blez.aniplex_clone.db.dao.WatchedDao
import com.blez.aniplex_clone.utils.Constants
import com.blez.aniplex_clone.utils.Constants.BASE_URL
import com.blez.aniplex_clone.utils.SettingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES) // read timeout
            .build()

    }

    @Provides
    @Singleton
    fun providesWatchedDao(appDB: AppDB) : WatchedDao{
        return appDB.watchedDao()
    }

    @Provides
    @Singleton
    fun providesAppDB(@ApplicationContext context: Context) : AppDB{
        return Room.databaseBuilder(context,
        AppDB::class.java,
        Constants.DB_NAME).build()
    }

    @Provides
    @Singleton
    fun providesApplicationContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun getAnimeInterface(retrofit: Retrofit) : AnimeInterface
    {
        return retrofit.create(AnimeInterface::class.java)
    }

    @Provides
    @Singleton
    fun getSettingManager(context: Context) : SettingManager{
        return SettingManager(context)
    }

}