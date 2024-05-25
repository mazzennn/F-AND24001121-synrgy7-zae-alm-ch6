package com.example.chapter_5.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.chapter_5.R
import com.example.chapter_5.databinding.FragmentProfileBinding
import com.example.chapter_5.helper.DataStoreManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        dataStoreManager = DataStoreManager.getInstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val storedUsername = dataStoreManager.username.firstOrNull() ?: ""
            val storedEmail = dataStoreManager.email.firstOrNull() ?: ""
            val storedPassword = dataStoreManager.password.firstOrNull() ?: ""

            binding.usernameTextField.setText(storedUsername)
            binding.emailTextField.setText(storedEmail)
            binding.passwordTextField.setText(storedPassword)
        }

        binding.btnUpdateProfile.setOnClickListener {
            lifecycleScope.launch {
                val username = binding.usernameTextField.text.toString().trim()
                val email = binding.emailTextField.text.toString().trim()
                val password = binding.passwordTextField.text.toString().trim()

                dataStoreManager.saveUser(username, email, password)
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogout.setOnClickListener(){
            viewLifecycleOwner.lifecycleScope.launch{
                dataStoreManager.clearUser()
                findNavController().navigate(R.id.loginFragment)
            }
        }

    }
}