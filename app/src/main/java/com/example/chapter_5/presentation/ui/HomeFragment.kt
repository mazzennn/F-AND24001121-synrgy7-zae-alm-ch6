package com.example.chapter_5.presentation.ui

import android.os.Bundle
import android.util.Log
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
import com.example.chapter_5.common.Resource
import com.example.chapter_5.databinding.FragmentHomeBinding
import com.example.chapter_5.helper.DataStoreManager
import com.example.chapter_5.presentation.adapter.MovieAdapter
import com.example.chapter_5.presentation.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MovieViewModel by viewModels()
//    private lateinit var viewModel: MovieViewModel

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieListState.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val movies = resource.data?.results ?: emptyList()
                        movieAdapter.updateMovies(movies)
                        Log.d("HomeFragment", "Received ${movies.size} movies")
                        movieAdapter.updateMovies(movies)
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), resource.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                        Log.d("HomeFragment", "${resource.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("HomeFragment", "Loading...")
                        // Show loading indicator if needed
                    }
                }
            }
        }
    }

}