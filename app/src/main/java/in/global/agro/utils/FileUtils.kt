package `in`.global.agro.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

object FileUtils {


    fun getAppExternalSpecificFiles(context: Context):File?{
        val file=context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if (file != null && !file.exists()){
            file.mkdirs()
        }

        return file
    }



    fun getSharedAppFiles(context: Context):File?{
        val files=context.filesDir
        if (files != null && !files.exists()){
            files.mkdirs()
        }
        return files
    }

    inline fun <T> sdk29AndUp(onSdk29: () -> T): T? {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            onSdk29()
        } else null
    }



    fun writeBitmapToFile(applicationContext: Context, bitmap: Bitmap): Uri {
        val name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString())
        val outputDir = File(applicationContext.filesDir, "Hello")

        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }

        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null

        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
        } finally {
            out?.let {
                try {
                    it.close()
                } catch (ignore: IOException) {
                }
            }
        }

        return Uri.fromFile(outputFile)
    }




}