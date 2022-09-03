package com.blez.aniplex_clone.network

import com.blez.aniplex_clone.utils.Constants.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
       fun getRetrofitInstance() : Retrofit{
           return Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
               .build()
       }
    }
}