package `in`.global.agro.callbacks

import `in`.global.agro.data.model.PurchaseModel
import `in`.global.agro.data.model.SalesModel
import android.view.View
import android.view.ViewGroup

interface PurchaseOptionsListener {

    fun onOptionsClick(purchaseModel: PurchaseModel, view: ViewGroup)

}