package com.example.chapter_5.presentation.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.chapter_5.R
import com.example.chapter_5.databinding.FragmentProfileBinding
import com.example.chapter_5.helper.DataStoreManager
import com.example.chapter_5.presentation.viewmodel.BlurViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStoreManager: DataStoreManager
    private var currentPhotoPath: String? = null

    private val blurViewModel: BlurViewModel by viewModels()

    private lateinit var profileImagePath: String

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

        blurImage()

        lifecycleScope.launch {
            val storedUsername = dataStoreManager.username.firstOrNull() ?: ""
            val storedEmail = dataStoreManager.email.firstOrNull() ?: ""
            val storedPassword = dataStoreManager.password.firstOrNull() ?: ""

            binding.usernameTextField.setText(storedUsername)
            binding.emailTextField.setText(storedEmail)
            binding.passwordTextField.setText(storedPassword)

            val userPhotoPath = dataStoreManager.getUserPhoto()
            if (userPhotoPath != null) {
                // Load the image using Glide or BitmapFactory
                val userPhotoBitmap = BitmapFactory.decodeFile(userPhotoPath)
                binding.tv1.setImageBitmap(userPhotoBitmap)
            } else {
                // Use default image if user photo path is null
                binding.tv1.setImageResource(R.drawable.default_user_photo)
            }
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

        binding.btnEditPhoto.setOnClickListener(){
            getContent.launch("image/*")
        }

        currentPhotoPath?.let { path ->
            val bitmap = BitmapFactory.decodeFile(path)
            binding.tv1.setImageBitmap(bitmap)
        }
    }
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            saveImageToFile(it)
        }
    }

    private fun saveImageToFile(imageUri: Uri) {
        val inputStream = requireContext().contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val storageDir = requireContext().getExternalFilesDir(null)
        val imageFile = File.createTempFile(
            "profile_photo_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )

        currentPhotoPath = imageFile.absolutePath

        val fos = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        fos.close()

        lifecycleScope.launch {
            dataStoreManager.saveUserPhoto(currentPhotoPath!!)
        }

        // Update ImageView with the new photo
        binding.tv1.setImageBitmap(bitmap)
    }

    private fun blurImage(){
        binding.btnApplyBlur.setOnClickListener {
            val profileImagePath = currentPhotoPath ?: return@setOnClickListener
            val blurLevel = binding.blurLevel.progress
            blurViewModel.applyBlur(profileImagePath, blurLevel, requireContext())
        }

        blurViewModel.blurredImageUri.observe(viewLifecycleOwner, Observer { uri ->
            uri?.let {
                binding.tv1.setImageURI(it)
            }
        })

        blurViewModel.isBlurring.observe(viewLifecycleOwner, Observer { isBlurring ->
            if (isBlurring) {
                Toast.makeText(context, "Blurring image...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Blur complete", Toast.LENGTH_SHORT).show()
            }
        })
    }
}