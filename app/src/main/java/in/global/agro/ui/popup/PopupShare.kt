package `in`.global.agro.ui.popup

import `in`.global.agro.callbacks.SharePopupCallback
import `in`.global.agro.databinding.PopupShareBinding
import android.view.View
import android.view.ViewGroup

class PopupShare(
    contextView: View,
    viewGroup: ViewGroup,
) : CustomPopup() {

    private lateinit var sharePopupCallback:SharePopupCallback
    private var binding:PopupShareBinding


    init {
        init(contextView, viewGroup)
        binding= PopupShareBinding.bind(contextView)

        binding.shareImage.setOnClickListener {
            sharePopupCallback.onShareClick(" Share Image")
        }
         binding.sharePdf.setOnClickListener {
            sharePopupCallback.onShareClick(" Share Pdf")
        }


    }


    fun setOnSharePopupCallback(sharePopupCallback: SharePopupCallback){
        this.sharePopupCallback=sharePopupCallback
    }
}