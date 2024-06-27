package com.example.chapter_5.presentation.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.chapter_5.workes.BlurWorker
import kotlinx.coroutines.launch

class BlurViewModel(): ViewModel() {

    private val _blurredImageUri = MutableLiveData<Uri?>()
    val blurredImageUri: LiveData<Uri?> get() = _blurredImageUri

    private val _isBlurring = MutableLiveData<Boolean>()
    val isBlurring: LiveData<Boolean> get() = _isBlurring

    fun applyBlur(imagePath: String, blurLevel: Int, context: Context) {
        viewModelScope.launch {
            _isBlurring.value = true

            val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
                .setInputData(
                    Data.Builder()
                        .putString("PROFILE_PICTURE_PATH", imagePath)
                        .putInt("BLUR_LEVEL", blurLevel)
                        .build()
                )
                .build()

            WorkManager.getInstance(context).enqueue(blurRequest)

            WorkManager.getInstance(context).getWorkInfoByIdLiveData(blurRequest.id)
                .observeForever { workInfo ->
                    if (workInfo != null && workInfo.state.isFinished) {
                        val outputData = workInfo.outputData
                        val outputUri = outputData.getString("OUTPUT_URI")

                        _blurredImageUri.value = outputUri?.let { Uri.parse(it) }
                        _isBlurring.value = false
                    }
                }
        }
    }
}