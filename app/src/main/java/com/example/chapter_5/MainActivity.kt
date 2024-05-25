package com.example.chapter_5

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.chapter_5.api.ApiClient
import com.example.chapter_5.helper.DataStoreManager
import com.example.chapter_5.model.response.MovieResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager.getInstance(this)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            val isLoggedIn = dataStoreManager.isLoggedIn.first()
            if (isLoggedIn) {
                ApiClient.instance.getMovieNowPlaying().enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                        Log.e("SimpleDataAPI", Gson().toJson(response.body()))
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.e("SimpleDataAPI", Gson().toJson(t.message))
                    }

                })
                findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
            } else {
                findNavController(R.id.nav_host_fragment).navigate(R.id.loginFragment)
            }
        }



    }
}
