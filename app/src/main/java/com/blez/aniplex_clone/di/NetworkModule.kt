package com.blez.aniplex_clone.di

import android.content.Context
import com.blez.aniplex_clone.Network.AnimeInterface
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {



    @Provides
    @Singleton
    fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun getAnimeInterface(retrofit: Retrofit) : AnimeInterface
    {
        return retrofit.create(AnimeInterface::class.java)
    }


}