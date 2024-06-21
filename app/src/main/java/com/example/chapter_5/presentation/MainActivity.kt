package com.example.chapter_5.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.chapter_5.R
import com.example.chapter_5.helper.DataStoreManager
import com.example.chapter_5.presentation.ui.MovieListState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    private val _movieListState = MutableLiveData<MovieListState>()
    val movieListState: LiveData<MovieListState> get() = _movieListState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager.getInstance(this)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            val isLoggedIn = dataStoreManager.isLoggedIn.first()
            if (isLoggedIn) {
                findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
            } else {
                findNavController(R.id.nav_host_fragment).navigate(R.id.loginFragment)
            }
        }
    }


}
