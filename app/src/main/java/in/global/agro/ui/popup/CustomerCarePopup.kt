package `in`.global.agro.ui.popup

import `in`.global.agro.callbacks.CustomerPopupCallback
import `in`.global.agro.databinding.PopupHelpBinding
import android.view.View
import android.view.ViewGroup

class CustomerCarePopup(
    contentView: View,
    view: ViewGroup,
    xOff: Float,
    yOff: Float
) : CustomPopup() {

    var binding: PopupHelpBinding
    private lateinit var customerPopupCallback: CustomerPopupCallback


    init {
        init(contentView, view, xOff, yOff)
        binding = PopupHelpBinding.bind(contentView)

        binding.whatsapp.setOnClickListener {
            customerPopupCallback.onCustomerCareClick("WhatsApp")
        }
        binding.email.setOnClickListener {
            customerPopupCallback.onCustomerCareClick("Email")
        }
        binding.Message.setOnClickListener {
            customerPopupCallback.onCustomerCareClick("Message")
        }
        binding.call.setOnClickListener {
            customerPopupCallback.onCustomerCareClick("Call")
        }
    }


    fun setOnCustomerPopupCallback(customerPopupCallback: CustomerPopupCallback) {
        this.customerPopupCallback = customerPopupCallback
    }


}