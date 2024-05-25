package com.example.chapter_5.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.chapter_5.databinding.FragmentDetailBinding
import com.example.chapter_5.model.response.Result
import com.example.chapter_5.viewmodel.MovieViewModel
import com.example.chapter_5.viewmodel.MovieViewModelFactory

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory.getInstance(requireContext())
    }

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
        observeViewModel(movieId)
    }

    private fun observeViewModel(movieId: Int) {
        viewModel.movieDetailResponse.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                null -> {
                    Toast.makeText(requireContext(), "Failed to load movies", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    bindMovieDetails(resources)
                }
            }
        }
        viewModel.getMovieDetails(movieId)
    }

    private fun bindMovieDetails(movie: Result) {
        val title = movie?.title
        val vote = movie?.voteAverage.toString()
        val popularity = movie?.popularity.toString()
        val voteCount = movie?.voteCount
        val overview = movie?.overview
        val release = movie?.releaseDate.toString()
        val poster = movie?.posterPath
        binding.titleText.text = title
        binding.releaseText.text = release
        binding.populartiyText.text = popularity
        binding.overviewText.text = overview

        val posterUrl = "https://image.tmdb.org/t/p/w500${poster}"
        Glide.with(this)
            .load(posterUrl)
            .into(binding.posterImageView)
    }


}