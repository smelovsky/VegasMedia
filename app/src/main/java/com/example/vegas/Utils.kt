package com.example.vegas

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

object Constants {
    val APP_FOLDER = "Vegas"
}

val outputPath: String
    get() {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).toString() +
                File.separator +
                Constants.APP_FOLDER +
                File.separator

        val folder = File(path)
        if (!folder.exists())
            folder.mkdirs()

        return path
    }

fun copyFileToExternalStorage(resourceId: Int, resourceName: String, context: Context): File {
    val pathSDCard = outputPath + resourceName
    try {
        val inputStream = context.resources.openRawResource(resourceId)
        inputStream.toFile(pathSDCard)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return File(pathSDCard)
}

fun InputStream.toFile(path: String) {
    File(path).outputStream().use { this.copyTo(it) }
}