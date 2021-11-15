package `in`.global.agro.callbacks

import `in`.global.agro.data.model.SalesModel
import android.view.View
import android.view.ViewGroup

interface SalesOptionsListener {

    fun onOptionsClick(salesModel: SalesModel,view:ViewGroup)

}