package com.example.chapter_5.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chapter_5.api.ApiClient
import com.example.chapter_5.api.RemoteDataSource
import com.example.chapter_5.helper.DataStoreManager
import com.example.chapter_5.repository.MovieRepository

class MovieViewModelFactory(val remoteDataSource: RemoteDataSource, val pref: DataStoreManager) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: MovieViewModelFactory? = null

        fun getInstance(context: Context): MovieViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MovieViewModelFactory(
                    RemoteDataSource(ApiClient.instance),
                    DataStoreManager(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(MovieRepository(remoteDataSource), pref) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
