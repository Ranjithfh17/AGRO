package `in`.global.agro.ui.dialog

import `in`.global.agro.R
import `in`.global.agro.callbacks.SharePopupCallback
import `in`.global.agro.callbacks.TaxPopupCallback
import `in`.global.agro.databinding.FragmentShareBinding
import `in`.global.agro.ui.dialog.CustomBottomSheet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class Share : CustomBottomSheet() {

    private lateinit var binding: FragmentShareBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShareBinding.inflate(inflater, container, false)

        binding.shareAsImage.setOnClickListener {
            sharePopupCallback.onShareClick("Share Image")
            dialog?.dismiss()
        }


        binding.shareAsPdf.setOnClickListener {
            sharePopupCallback.onShareClick("Share Pdf")
            dialog?.dismiss()

        }


        return binding.root
    }


    companion object{
        private lateinit var sharePopupCallback: SharePopupCallback

        fun setOnSharePopupCallback(sharePopupCallback: SharePopupCallback){
            this.sharePopupCallback=sharePopupCallback
        }
    }

}