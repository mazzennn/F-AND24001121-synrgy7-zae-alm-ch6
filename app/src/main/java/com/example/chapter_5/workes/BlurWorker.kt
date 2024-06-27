package com.example.chapter_5.workes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

private const val TAG = "BlurWorker"
class BlurWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        val appContext = applicationContext

//        makeStatusNotification("Blurring image", appContext)
        Log.d("BlurWorker", "Started blurring image")

        return try {
            val profilePicturePath = inputData.getString("PROFIL_PICTURE_PATH") ?:return Result.failure()
            val blurLevel = inputData.getInt("BLUR_LEVEL", 25)

            Log.d("BlurWorker", "Profile picture path: $profilePicturePath")
            Log.d("BlurWorker", "Blur level: $blurLevel")

            if (profilePicturePath == null) {
                Log.e("BlurWorker", "Profile picture path is null")
                return Result.failure()
            }

            val picture = BitmapFactory.decodeFile(profilePicturePath)
            val output = blurBitmap(picture, appContext, blurLevel)

            val outputUri = writeBitmapToFile(appContext, output)

            val outputData = Data.Builder()
                .putString("OUTPUT_URI", outputUri)
                .build()

            Log.d("BlurWorker", "Output is $outputUri")

            Result.success(outputData)
        } catch (throwable: Throwable) {
            Log.e("BlurWorker", "Error applying blur", throwable)
            Result.failure()
        }
    }

    private fun blurBitmap(bitmap: Bitmap, context: Context, blurLevel: Int): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(output)
        val paint = Paint()
        paint.isAntiAlias = true

        paint.maskFilter = android.graphics.BlurMaskFilter(blurLevel.toFloat(), android.graphics.BlurMaskFilter.Blur.NORMAL)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        return output
    }

    private fun writeBitmapToFile(context: Context, bitmap: Bitmap): String {
        val name = String.format("blur-output-%s.png", UUID.randomUUID().toString())
        val outputDir = File(context.filesDir, "blur_outputs")
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val outputFile = File(outputDir, name)
        var outputStream: FileOutputStream? = null

        return try {
            outputStream = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputFile.absolutePath
        } catch (e: IOException) {
            Log.e("BlurWorker", "Error writing bitmap to file", e)
            ""
        } finally {
            outputStream?.close()
        }
    }

}