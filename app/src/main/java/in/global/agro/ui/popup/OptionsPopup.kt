package `in`.global.agro.ui.popup

import `in`.global.agro.callbacks.OptionsPopupCallback
import `in`.global.agro.databinding.OptionsPopupBinding
import android.view.View
import android.view.ViewGroup

class OptionsPopup(contentView: View, viewGroup: ViewGroup, xOff: Float, yOff: Float) :
    CustomPopup() {
    private var binding: OptionsPopupBinding

    private lateinit var optionsPopupCallback: OptionsPopupCallback

    init {

        init(contentView, viewGroup, xOff, yOff)


        binding = OptionsPopupBinding.bind(contentView)

        binding.deleteItem.setOnClickListener {
            optionsPopupCallback.onOptionsClick("delete")
        }
        binding.share.setOnClickListener {
            optionsPopupCallback.onOptionsClick("share")
        }

        binding.save.setOnClickListener {
            optionsPopupCallback.onOptionsClick("save")
        }

    }


    fun setOnOptionsCallBack(optionsPopupCallback: OptionsPopupCallback) {
        this.optionsPopupCallback = optionsPopupCallback
    }


}