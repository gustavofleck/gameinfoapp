package com.gustavo.architectureapp.data.storage

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream

class FileManager(
    private val context: Context
) {

    fun createShareableImageFile(image: ImageView) {
        val stream = createFileStream()
        val bitmap = image.drawable.toBitmap()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
    }

    fun createUriForShareableFile(): Uri {
        val file = createFile()
        return FileProvider.getUriForFile(context, "com.gustavo.architectureapp.fileprovider", file)
    }

    private fun createFileStream() =
        FileOutputStream(createFile())

    private fun createFile() =
        File(createCacheDir(), "shareableImage.jpg")


    private fun createCacheDir(): File {
        val cachePath = File(context.cacheDir, "gameimages")
        with(cachePath) {
            if (exists().not()) {
                mkdirs()
            }
            return this
        }
    }



}