package com.example.chapter_5.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter_5.R
import com.example.chapter_5.api.ApiClient
import com.example.chapter_5.databinding.FragmentHomeBinding
import com.example.chapter_5.model.MovieAdapter
import com.example.chapter_5.model.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(context)
        movieAdapter = MovieAdapter(emptyList()) { movieId ->
            val bundle = Bundle().apply {
                putInt("movieId", movieId)
            }

            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
        binding.recyclerViewMovies.adapter = movieAdapter

        fetchAllMovies()
    }

    private fun fetchAllMovies(){
        ApiClient.instance.getMovieNowPlaying().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { movieList ->
                        movieAdapter.updateMovies(movieList)
                    }
                } else {
                    Log.e("MovieActivity", "Response code: ${response.code()}")
                }
                }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("HomeFragment", "Failed to fetch movies", t)
            }

        })
    }

}