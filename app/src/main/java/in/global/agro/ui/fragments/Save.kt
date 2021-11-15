package `in`.global.agro.ui.fragments

import `in`.global.agro.R
import `in`.global.agro.databinding.FragmentSalesBinding
import `in`.global.agro.databinding.FragmentSaveBinding
import `in`.global.agro.utils.ImageHelper
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.Exception


class Save : Fragment(R.layout.fragment_save) {
    private lateinit var binding: FragmentSaveBinding
    private lateinit var savePdfFile: ActivityResultLauncher<String>
    private lateinit var byteArray: ByteArray

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSaveBinding.bind(view)

        CoroutineScope(Dispatchers.Main).launch {

            binding.savePdf.setOnClickListener {
                val bitmap = ImageHelper.viewToBitmap(binding.contentLayout)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                 byteArray = stream.toByteArray()
                bitmap.recycle()
                savePdfFile.launch("sales.pdf")

            }



        }

        savePdfFile = registerForActivityResult(ActivityResultContracts.CreateDocument()) {

//                if (it == null) {
//                    return@registerForActivityResult
//                }
            try {
                requireContext().contentResolver.openOutputStream(it).use { outputStream ->

                    if (outputStream != null){
                        outputStream.write(byteArray)
                        outputStream.flush()
                    }
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
            }

        }

    }


}