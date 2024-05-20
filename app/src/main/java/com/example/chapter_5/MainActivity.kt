package com.example.chapter_5

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.chapter_5.api.ApiClient
import com.example.chapter_5.model.response.MovieResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

//    private val viewModel: MovieViewModel by viewModels {
//        MovieViewModelFactory.getInstance(this)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiClient.instance.getMovieNowPlaying().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.e("SimpleDataAPI", Gson().toJson(response.body()))
                Log.d("SimpleDataAPI", "Masook")
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("SimpleDataAPI", Gson().toJson(t.message))
            }

        })

//        ApiClient.instance.getMovieDetail(movieId = "653346").enqueue(object : Callback<Result> {
//            override fun onResponse(call: Call<Result>, response: Response<Result>) {
//                Log.e("SimpleDataAPIDetail", Gson().toJson(response.body()))
//            }
//
//            override fun onFailure(call: Call<Result>, t: Throwable) {
//                Log.e("SimpleDataAPIDetail", Gson().toJson(t.message))
//            }
//        })

//        val request = RatingRequest(8.5)
//        ApiClient.instance.addRating(movieId = "653346", request = request)
//            .enqueue(object : Callback<RatingResponse> {
//                override fun onResponse(
//                    call: Call<RatingResponse>,
//                    response: Response<RatingResponse>
//                ) {
//                    Log.e("SimpleDataAPIRating", Gson().toJson(response.body()))
//                }
//
//                override fun onFailure(call: Call<RatingResponse>, t: Throwable) {
//                    Log.e("SimpleDataAPIRating", Gson().toJson(t.message))
//                }
//            })

//        viewModel.searchMovie("Agak Laen").observe(this) { resources ->
//            when (resources.status) {
//                Status.SUCCESS -> {
//                    val data = resources.data
//                    Log.e("SimpleRetrofit", Gson().toJson(data))
//                }
//
//                Status.ERROR -> {
//                    val response = resources.message
//                    Log.e("SimpleRetrofit", response.toString())
//                }
//
//                Status.LOADING -> {}
//            }
        }

//        viewModel.getMoviePopular().observe(this){
//
//        }
//
//        viewModel.getMovieNowPlaying()
//        viewModel.movieResponse.observe(this){
//
//        }
//    }
}
