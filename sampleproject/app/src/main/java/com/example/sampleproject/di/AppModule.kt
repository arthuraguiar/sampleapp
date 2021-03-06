package com.example.sampleproject.di

import com.example.sampleproject.api.EventosApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(EventosApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideEventosApi(retrofit: Retrofit): EventosApi =
        retrofit.create(EventosApi::class.java)
}