package `in`.global.agro.callbacks

import `in`.global.agro.data.model.ProductModel

interface ProductCallback {

    fun onDeleteProduct(productModel: ProductModel){}

    fun onSelectProduct(productModel: ProductModel){}
}