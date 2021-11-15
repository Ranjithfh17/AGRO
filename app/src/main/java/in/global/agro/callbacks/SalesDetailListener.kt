package `in`.global.agro.callbacks

import `in`.global.agro.data.model.SalesModel

interface SalesDetailListener {

    fun onDetailClick(salesModel: SalesModel)
}