package com.example.chapter_5.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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
        dataStoreManager = DataStoreManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val storedUsername = dataStoreManager.username.firstOrNull() ?: ""
            val storedEmail = dataStoreManager.email.firstOrNull() ?: ""
            val storedPassword = dataStoreManager.password.firstOrNull() ?: ""

            // Menampilkan data di EditText
            binding.usernameTextField.setText(storedUsername)
            binding.emailTextField.setText(storedEmail)
            binding.passwordTextField.setText(storedPassword)
        }
    }
}