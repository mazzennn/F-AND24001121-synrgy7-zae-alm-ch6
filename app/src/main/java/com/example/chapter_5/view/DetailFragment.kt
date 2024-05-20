package com.example.chapter_5.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chapter_5.api.ApiClient
import com.example.chapter_5.databinding.FragmentDetailBinding
import com.example.chapter_5.model.response.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("movieId")?:return
        fetchMovieDetails(movieId)
    }

    private fun fetchMovieDetails(movieId: Int){
        ApiClient.instance.getMovieDetail(movieId = movieId.toString()).enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful) {
                    response.body()?.let { movie ->
                        bindMovieDetails(movie)
                    }
                } else {
                    Log.e("MovieActivity", "Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.e("DetailFragment", "Failed to fetch movies", t)
            }

        })
    }

    private fun bindMovieDetails(movie: Result) {
        val title = movie?.title
        val vote = movie?.voteAverage.toString()
        val voteCount = movie?.voteCount
        val overview = movie?.overview
        val release = movie?.releaseDate.toString()
        binding.titleText.text = title
        binding.releaseText.text = release
        binding.genreText.text = vote
        binding.overviewText.text = overview
    }

}