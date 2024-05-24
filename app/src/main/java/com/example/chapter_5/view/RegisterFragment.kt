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
import com.example.chapter_5.databinding.FragmentRegisterBinding
import com.example.chapter_5.helper.DataStoreManager
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        dataStoreManager = DataStoreManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener{
            val username = binding.usernameTextField.text.toString().trim()
            val email = binding.emailTextField.text.toString().trim()
            val password = binding.passwordTextField.text.toString().trim()
            val confirmPassword = binding.confirmPasswordTextField.text.toString().trim()
            if(username.isNotEmpty() && email.isNotEmpty()&&
                password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if(password == confirmPassword){
                    lifecycleScope.launch {
                        dataStoreManager.saveUser(username, email, password)
                        Toast.makeText(requireContext(), "Pedaftaran Berhasil", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                }else{
                    Toast.makeText(requireContext(), "Password Tidak Cocok", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Tolong isi dengan lengkap", Toast.LENGTH_SHORT).show()
            }
        }
        binding.signInText.setOnClickListener(){
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}