package com.example.chapter_5.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter_5.R
import com.example.chapter_5.databinding.FragmentHomeBinding
import com.example.chapter_5.helper.DataStoreManager
import com.example.chapter_5.model.MovieAdapter
import com.example.chapter_5.viewmodel.MovieViewModel
import com.example.chapter_5.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory.getInstance(requireContext())
    }
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        dataStoreManager = DataStoreManager.getInstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val storedUsername = dataStoreManager.username.firstOrNull() ?: ""

            binding.tv1.text = "Hello $storedUsername"
        }

        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(context)
        movieAdapter = MovieAdapter(emptyList()) { movieId ->
            val bundle = Bundle().apply {
                putInt("movieId", movieId)
            }

            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
        binding.recyclerViewMovies.adapter = movieAdapter
        observeViewModel()

        binding.btnProfile.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

    }

    private fun observeViewModel() {
        viewModel.movieResponse.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                null -> {
                    Toast.makeText(requireContext(), "Failed to load movies", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    movieAdapter.updateMovies(resources.results) // Assuming MovieResponse has a property `results`
                }
            }
        }
        viewModel.getMovieNowPlaying()
    }


}