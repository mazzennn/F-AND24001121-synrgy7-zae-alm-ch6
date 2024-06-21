package com.example.chapter_5.di

import android.util.Log
import com.example.chapter_5.api.ApiClient
import com.example.chapter_5.data.remote.MovieApi
import com.example.chapter_5.data.repository.MovieRepositoryImpl
import com.example.chapter_5.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    fun provideRetrofit(): Retrofit{
//        return Retrofit.Builder().baseUrl("")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi {
        Log.d("AppModule", "Providing ApiClient instance")
        return ApiClient.instance
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieApi: MovieApi): MovieRepository {
        Log.d("AppModule", "Providing MovieRepository instance")
        return MovieRepositoryImpl(movieApi)
    }
}