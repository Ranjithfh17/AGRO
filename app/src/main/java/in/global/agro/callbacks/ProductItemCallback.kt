package `in`.global.agro.callbacks

import `in`.global.agro.data.model.ProductItem

interface ProductItemCallback {

    fun onSendProduct(productItem: ProductItem)

}