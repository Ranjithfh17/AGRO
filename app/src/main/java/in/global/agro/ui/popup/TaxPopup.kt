package `in`.global.agro.ui.popup

import `in`.global.agro.callbacks.TaxPopupCallback
import `in`.global.agro.databinding.PopupTaxBinding
import android.view.View
import android.view.ViewGroup

class TaxPopup(contentView: View, view: ViewGroup, xOff: Float, yOff: Float) : CustomPopup() {

    private lateinit var binding:PopupTaxBinding
    private lateinit var taxPopupCallback: TaxPopupCallback

    init {
        init(contentView, view, xOff, yOff)
        binding= PopupTaxBinding.bind(contentView)

        binding.includeTax.setOnClickListener {
            taxPopupCallback.onTaxClick("Include Tax")
        }

        binding.excludeTax.setOnClickListener {
            taxPopupCallback.onTaxClick("Exclude Tax")
        }




    }

    fun setOnTaxPopupCallback(taxPopupCallback: TaxPopupCallback){
        this.taxPopupCallback=taxPopupCallback
    }

}