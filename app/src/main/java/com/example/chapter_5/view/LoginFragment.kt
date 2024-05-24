package com.example.chapter_5.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.chapter_5.R
import com.example.chapter_5.databinding.FragmentLoginBinding
import com.example.chapter_5.helper.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        dataStoreManager = DataStoreManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener(){
            val username = binding.usernameTextField.text.toString().trim()
            val password = binding.passwordTextField.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()){
                lifecycleScope.launch {
                    val storedUsername = dataStoreManager.username.first()
                    val storedPassword = dataStoreManager.password.first()
                    if(username == storedUsername && password == storedPassword){
                        val bundle = Bundle().apply {
                            putString("username", storedUsername)
                        }

                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment, bundle)
                    }
                }
            }
        }
    }
}